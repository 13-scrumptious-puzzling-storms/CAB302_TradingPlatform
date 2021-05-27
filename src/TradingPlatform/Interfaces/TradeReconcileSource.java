package TradingPlatform.Interfaces;

import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeRecon;

import java.util.ArrayList;

public interface TradeReconcileSource {
    /**
     * Inserts a trade reconciliation into the tradeRecon table
     * @param recon the reconciliation that will be inserted
     */
    void insertTradeRecon(TradeRecon recon);

    /**
     * Gets the current asset types that can be reconciled (the asset types have pending reconcilable trades)
     * @return An arraylist of AssetTypes that have pending reconcilable trades
     */
    ArrayList<Integer> getCurrentReconcilableAssetTypeIds();

    /**
     * Gets the current sell orders for an asset type ordered by Price then CreatedTime
     * @param assetTypeId The assetTypeId of the buy order
     * @return An arraylist of trade orders
     */
    ArrayList<TradeOrder> getCurrentSellOrders(int assetTypeId);

    /**
     * Gets the current buy orders for an asset type ordered by CreatedTime
     * @param assetTypeId The assetTypeId of the buy order
     * @return An arraylist of trade orders
     */
    ArrayList<TradeOrder> getCurrentBuyOrders(int assetTypeId);

    /**
     * Gets the oldest buy order that is of the specified asset type, and is willing to pay the minPrice or more.
     * @param assetTypeId The assetTypeId of the buy order
     * @param minPrice The minimum price for the buy order
     * @return A matching buy trade order, or null if there are none.
     */
    TradeOrder getMatchingBuyOrder(int assetTypeId, int minPrice);

}
