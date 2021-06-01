package TradingPlatform;

import java.io.IOException;

import static TradingPlatform.ClientApp.networkManager;

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
     * returns the name of the asset as a string
     * @return assetName
     */
    public String toString(){
        return assetName;
    }

    public static String[] getAllAssetNames() throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCAssetType", "getAllAssetNames", new String[]{});
        return response.getArguments();
    }
}
