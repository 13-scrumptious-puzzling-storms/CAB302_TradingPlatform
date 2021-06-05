package TradingPlatform;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static TradingPlatform.ClientApp.networkManager;

/**
 * Creates a new instance of an asset. Creates calls to the database
 * regarding information created by the database.
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

    /**
     * Retrieves all Asset Names from the database
     * @return all asset names that are listed in the database
     */
    public static String[] getAllAssetNames() {
        try {
            Request response = NetworkManager.GetResponse("JDBCAssetType", "getAllAssetNames", new String[]{});
            return response.getArguments();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns all Asset types from the database in an Arraylist
     * @return All asset types from the database
     */
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
