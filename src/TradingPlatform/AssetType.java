package TradingPlatform;

/**
 * Creates a new instance of an asset
 */
public class AssetType {

    private int assetId;
    private String assetName;

    /**
     * creates a new instance of an asset from the assetName
     * @param assetName The name of the asset
     */
    public AssetType(String assetName){
        this.assetName = assetName;
    }

    /**
     * grabs the asset from the db
     * @param assetId The assetID of the asset.
     */
    public AssetType(int assetId){
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
