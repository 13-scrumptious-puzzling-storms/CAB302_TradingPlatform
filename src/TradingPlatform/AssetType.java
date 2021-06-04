package TradingPlatform;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
        this.assetId = -1;
    }


    /**
     * returns the name of the asset as a string
     * @return assetName
     */
    public String toString(){
        return assetName;
    }

    public static String[] getAllAssetNames() throws IOException, ClassNotFoundException {
        Request response = NetworkManager.GetResponse("JDBCAssetType", "getAllAssetNames", new String[]{});
        return response.getArguments();
    }

    public static ArrayList<AssetType> getAllAssetTypes() {
        ArrayList<AssetType> assets = new ArrayList<>();
        try {
            Request response = NetworkManager.GetResponse("JDBCAssetType", "getAllAssets");
            // String[numAssets][id, assetName] response
            String[][] typeStringArr = response.getDoubleString();
            for (int i = 0; i < typeStringArr.length; i++){
                AssetType asset = new AssetType(typeStringArr[i][1]);
                asset.setAssetId(Integer.parseInt(typeStringArr[i][0]));
                assets.add(asset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assets;
    }

    /**
     * Sets the asset Id. Private because the id should only be set when retrieving the asset from the server.
     * @param assetId the ID of the asset
     */
    private void setAssetId(int assetId){
        this.assetId = assetId;
    }

    /**
     * @return The ID of the asset
     */
    public int getAssetId() {
        // If not set, get it from the server
        if (assetId == -1){
            try {
                Request response = NetworkManager.GetResponse("JDBCAssetType", "getAssetId", new String[]{ assetName} );
                // String[id] response
                assetId = Integer.parseInt(response.getArguments()[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return assetId;
    }

    /**
     * @return the name of the asset
     */
    public String getAssetName() {
        return assetName;
    }
}
