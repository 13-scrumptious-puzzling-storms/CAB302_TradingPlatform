package TradingPlatform;

/**
 * Creates a new instance of either a BUY or SELL order for a trade
 */
public class Trade{

    private boolean type;
    private Object asset;
    private int quantity;
    private int organisation;

    /**
     * Creates a new trade instance
     * @param type
     * @param asset
     * @param quantity
     */
    public Trade(boolean type, Object asset, int quantity, int organisationId){
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.organisation = organisationId;
    }

    /**
     * returns the amount of credits needed for a trade
     * @param asset
     * @param quantity
     * @return price
     */
    public float value(String asset, int quantity){
        return 0;
    }

    /**
     * Sets the type of a trade to either BUY or SELL
     * @param type
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
     * @param asset
     */
    public void setAsset(Object asset){

    }

    /**
     * Gets the asset from the trade
     * @return asset
     */
    public Object getAsset(){
        return 0;
    }

    /**
     * Sets the quantity of assets in the trade
     * @param quantity
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
