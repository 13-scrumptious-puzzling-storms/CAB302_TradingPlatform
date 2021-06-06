package TradingPlatform.TradeReconciliation;

import TradingPlatform.JDBCDataSources.*;
import TradingPlatform.NetworkProtocol.DBConnection;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Trade reconcile is used to reconcile all valid trades. It updates org asset quantities, credits, and reconciles
 * buy and sell orders that are compatible with each other.
 */
public class TradeReconcile {
    /**
     * This method reconciles all current outstanding trades
     * NOTE: When a buy order is first placed, the max price for the order is deducted from the org's credits.
     *       When a sell order is first placed, the assets are deducted from the org's assets.
     *
     *       This means that when a trade is reconciled, it does not need to remove the selling org's assets,
     *       and it does not need to deduct from the buying org's credits.
     */
    public static void ReconcileCurrentTrades(Connection connection){
        // Get the connections to the database
        var reconcileSource = new JDBCTradeReconcileSource(connection);
        var orgUnitSource = new JDBCOrganisationalUnit(connection);
        var orgAssetSource = new JDBCOrganisationalAsset(connection);
        var tradeSource = new JDBCTradeDataSource(connection);

        // Need to have the trade lock to reconcile trades
        synchronized (JDBCThreadLock.UpdateDbLock){
            ArrayList<Integer> tradableAssetTypes = reconcileSource.getCurrentReconcilableAssetTypeIds();

            for (int assetTypeId : tradableAssetTypes) {
                ArrayList<TradeOrder> sellOrders = reconcileSource.getCurrentSellOrders(assetTypeId);
                for (TradeOrder sellOrder : sellOrders){
                    // Keep reconciling buy orders to the sell order while the sell order has remaining items
                    while (sellOrder.getRemainingQuantity() > 0) {

                        TradeOrder buyOrder = reconcileSource.getMatchingBuyOrder(assetTypeId, sellOrder.getPrice());
                        // Buy order will be null if there are no matching buy orders
                        if (buyOrder != null) {
                            // Decrease the sell and buy order's remaining quantity
                            int quantity = Math.min(sellOrder.getRemainingQuantity(), buyOrder.getRemainingQuantity());
                            sellOrder.reduceRemainingQuantity(quantity);
                            buyOrder.reduceRemainingQuantity(quantity);
                            tradeSource.setRemaining(sellOrder.getTradeOrderId(), sellOrder.getRemainingQuantity());
                            tradeSource.setRemaining(buyOrder.getTradeOrderId(), buyOrder.getRemainingQuantity());

                            // Give the credits to the seller
                            int tradePrice = quantity * sellOrder.getPrice();
                            int sellOrgUnitId = orgAssetSource.getOrganisationAssetOrgUnitID(sellOrder.getOrganisationAssetId());
                            int sellOrgCurrentCredits = orgUnitSource.getOrganisationalUnitCredits(sellOrgUnitId);
                            orgUnitSource.UpdateOrganisationalUnitCredits(sellOrgUnitId, sellOrgCurrentCredits + tradePrice);

                            // Refund the buying organisation unit the difference between the sell order price and the buy order price
                            int priceDiff = (quantity * buyOrder.getPrice()) - tradePrice;
                            int buyOrgUnitId = orgAssetSource.getOrganisationAssetOrgUnitID(buyOrder.getOrganisationAssetId());
                            int buyOrgCurrentCredits = orgUnitSource.getOrganisationalUnitCredits(buyOrgUnitId);
                            orgUnitSource.UpdateOrganisationalUnitCredits(buyOrgUnitId, buyOrgCurrentCredits + priceDiff);

                            // Give the assets to the buyer
                            int buyOrgAssetQuantity = orgAssetSource.getOrganisationAssetQuantity(buyOrder.getOrganisationAssetId());
                            orgAssetSource.UpdateOrganisationAssetQuantity(buyOrder.getOrganisationAssetId(), buyOrgAssetQuantity + quantity);

                            // Insert a tradeReconcile item into the TradeRecon table in the database
                            TradeRecon recon = new TradeRecon(sellOrder.getTradeOrderId(), buyOrder.getTradeOrderId(), quantity);
                            reconcileSource.insertTradeRecon(recon);
                        }
                        else // There are no compatible buy orders
                            break;
                    }
                }
            }
        }
    }
}
