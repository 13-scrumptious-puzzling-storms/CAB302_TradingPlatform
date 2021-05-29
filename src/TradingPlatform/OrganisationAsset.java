package TradingPlatform;

import java.io.IOException;
import java.util.ArrayList;

/**
 * OrganisationAssets stores an asset owned by each OrganisationalUnit and records unique ID for this pair
 */
public class OrganisationAsset {
    private int organisationAssetID;
    private int organisationUnitID;
    private AssetType assetType;
    private int quantity;
    private static NetworkManager networkManager = ClientApp.networkManager;

    // GUI OrganisationAsset Constructor
    public OrganisationAsset(int organisationAssetID, int organisationUnitID, AssetType assetType, int quantity){
        this.organisationAssetID = organisationAssetID;
        this.organisationUnitID = organisationUnitID;
        this.assetType = assetType;
        this.quantity = quantity;
    }

    public OrganisationAsset() {

    }

    // JDBC Constructor
//    public OrganisationAsset(int OrganisationAssetID, Connection connection){
//        // Get OrganisationAsset data from database ...
//
//        // Get fields from database ... fill this in
//
//
//        // result set rs ...
//        int assetTypeId = rs.getInt("AssetTypeID");
//        assetType = new AssetType(assetTypeId);
//    }

    // Get Methods below ...
    public static String[][] getOrganisationalUnitAssetTable(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetsQuantity", new String[] {String.valueOf(orgID)});
        String[][] result = response.getDoubleString();
        return result;
    }


    public int getOrganisationAssetID() {
        return organisationAssetID;
    }

    public int getOrganisationUnitID() {
        return organisationUnitID;
    }

    public int getQuantity() {
        return quantity;
    }

    public AssetType getAssetType() {
        return assetType;
    }
}
