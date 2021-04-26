package TradingPlatform;

/**
 * Creates a new instance of an asset
 */
public class Asset {

    private int assetId;
    private String assetName;

    /**
     * creates a new instance of an asset from the assetName
     * @param assetName
     */
    public Asset(String assetName){
        this.assetName = assetName;
    }

    /**
     * grabs the assetID from the db
     * @param assetId
     */
    public Asset(int assetId){
        this.assetId = assetId;
    }

    /**
     * returns the name of the asset as a string
     * @return assetName
     */
    public String toString(){
        return assetName;
    }
}
