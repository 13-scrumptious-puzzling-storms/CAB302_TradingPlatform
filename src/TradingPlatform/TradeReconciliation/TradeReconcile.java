package TradingPlatform.TradeReconciliation;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.NetworkProtocol.DBConnection;

import java.sql.Connection;
import java.util.ArrayList;

public class TradeReconcile implements Runnable {

    private static final int SLEEP_TIME = 1000; // Wakes up every 1 second(s)
    private static Connection connection;
    private final Object tradeLock;
    private static JDBCTradeReconcileSource reconcileSource;
    private static JDBCOrganisationalUnit orgUnitSource;
    private static JDBCOrganisationalAsset orgAssetSource;


    public TradeReconcile(Object tradeLock){
        this.tradeLock = tradeLock;
        try {
            connection = DBConnection.getInstance();
            reconcileSource = new JDBCTradeReconcileSource(connection);
            orgUnitSource = new JDBCOrganisationalUnit(connection);
            orgAssetSource = new JDBCOrganisationalAsset(connection);
            System.out.println("Connection to database successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            ReconcileCurrentTrades();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                if (Thread.interrupted()){
                    return;
                }
            }
        }
    }

    /**
     * This method reconciles all current outstanding trades
     * NOTE: When a buy order is first placed, the max price for the order is deducted from the org's credits.
     *       When a sell order is first placed, the assets are deducted from the org's assets.
     *
     *       This means that when a trade is reconciled, it does not need to remove the selling org's assets,
     *       and it does not need to deduct from the buying org's credits.
     */
    public void ReconcileCurrentTrades(){
        // Need to have the trade lock to reconcile trades
        synchronized (tradeLock){
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
//                        tradeSource.setRemaining(sellOrder.getTradeOrderId(), sellOrder.getRemainingQuantity() - quantity);
//                        tradeSource.setRemaining(buyOrder.getTradeOrderId(), buyOrder.getRemainingQuantity() - quantity);

                            // Give the credits to the seller
                            int tradePrice = quantity * sellOrder.getPrice();
                            int sellOrgUnitId = orgAssetSource.getOrganisationAssetOrgUnitID(sellOrder.getOrganisationAssetId());
                            int sellOrgCurrentCredits = orgUnitSource.getOrganisationalUnitCredits(sellOrgUnitId);
                            orgUnitSource.UpdateOrganisationalunitCredits(sellOrgUnitId, sellOrgCurrentCredits + tradePrice);

                            // Refund the buying organisation unit the difference between the sell order price and the buy order price
                            int priceDiff = (quantity * buyOrder.getPrice()) - tradePrice;
                            int buyOrgUnitId = orgAssetSource.getOrganisationAssetOrgUnitID(buyOrder.getOrganisationAssetId());
                            int buyOrgCurrentCredits = orgUnitSource.getOrganisationalUnitCredits(buyOrgUnitId);
                            orgUnitSource.UpdateOrganisationalunitCredits(buyOrgUnitId, buyOrgCurrentCredits + priceDiff);

                            // Give the assets to the buyer
                            int buyOrgAssetQuantity = orgAssetSource.getOrganisationAssetQuantity(buyOrder.getOrganisationAssetId());
                            orgAssetSource.UpdateOrganisationAssetQuantity(buyOrder.getOrganisationAssetId(), buyOrgAssetQuantity + quantity);

                        }
                        else // There are no compatible buy orders
                            break;
                    }
                }
            }
        }
    }
}
