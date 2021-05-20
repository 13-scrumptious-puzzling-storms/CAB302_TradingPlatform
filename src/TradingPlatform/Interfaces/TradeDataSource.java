package TradingPlatform.Interfaces;

import TradingPlatform.AssetType;

public interface TradeDataSource {

    void addTradeOrder(int orgAssetId, int quantity, int type, int price);

    /**
     * returns the amount of credits needed for a trade
     * @param asset The trade asset
     * @param quantity The number of assets
     * @return price
     */
    float value(String asset, int quantity);

    /**
     * Sets the type of a trade to either BUY or SELL
     * @param type True for buy, false for sell.
     */
    void setType(boolean type);

    /**
     * Gets the type of Trade, either BUY or SELL
     * @return type
     */
    String GetType();

    /**
     * Sets the asset used for the trade
     * @param asset The asset the trade will be for.
     */
    void setAsset(AssetType asset);

    /**
     * Gets the asset from the trade
     * @return asset
     */
    AssetType getAsset();

    /**
     * Sets the quantity of assets in the trade
     * @param quantity The quantity of assets in the trade
     */
    void setQuantity(int quantity);

    /**
     * Gets the quantity of assets in the trade
     * @return quantity
     */
    int getQuantity();

    /**
     * Returns the organisation linked to the current trade
     * @return organisation
     */
    String getOrganisation();
}
