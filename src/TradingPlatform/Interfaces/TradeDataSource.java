package TradingPlatform.Interfaces;

import TradingPlatform.AssetType;

public interface TradeDataSource {

    void addTradeOrder(int orgAssetId, int quantity, int type, int price);

    /**
     * returns the amount of credits needed for a trade
     * @return price
     */
    int value(int tradeId);

    /**
     * Gets the type of Trade, either BUY or SELL
     * @return type
     */
    String GetType(int tradeId);

    /**
     * Gets the asset from the trade
     * @return asset
     */
    int getAsset(int tradeId);

    /**
     * Gets the quantity of assets in the trade
     * @return quantity
     */
    int getQuantity(int tradeId);

    /**
     * Returns the organisation linked to the current trade
     * @return organisation
     */
//    String getOrganisation();

    /**
     * Sets the amount of assets remaining in the order
     * @param amount
     */
    void setRemaining(int tradeId, int amount);
}
