package TradingPlatform;

/**
 * Creates a new instance of either a BUY or SELL order for a trade
 */
public class Trade{

    private boolean type;
    private AssetType asset;
    private int quantity;
    private int organisation;

    /**
     * Creates a new trade instance
     * @param type The trade type (true for buy, false for sell).
     * @param asset The trade asset.
     * @param quantity The quantity of the asset for the trade.
     * @param organisationId The organisation initiating the trade order.
     */
    public Trade(boolean type, AssetType asset, int quantity, int organisationId){
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.organisation = organisationId;
    }

    /**
     * returns the amount of credits needed for a trade
     * @param asset The trade asset
     * @param quantity The number of assets
     * @return price
     */
    public float value(String asset, int quantity){
        return 0;
    }

    /**
     * Sets the type of a trade to either BUY or SELL
     * @param type True for buy, false for sell.
     */
    public void setType(boolean type){

    }

    /**
     * Gets the type of Trade, either BUY or SELL
     * @return type
     */
    public String GetType(){
        return "";
    }

    /**
     * Sets the asset used for the trade
     * @param asset The asset the trade will be for.
     */
    public void setAsset(AssetType asset){

    }

    /**
     * Gets the asset from the trade
     * @return asset
     */
    public AssetType getAsset(){
        return null;
    }

    /**
     * Sets the quantity of assets in the trade
     * @param quantity The quantity of assets in the trade
     */
    public void setQuantity(int quantity){

    }

    /**
     * Gets the quantity of assets in the trade
     * @return quantity
     */
    public int getQuantity(){
        return 0;
    }

    /**
     * Returns the organisation linked to the current trade
     * @return organisation
     */
    public String getOrganisation(){
        return "";
    }
}
