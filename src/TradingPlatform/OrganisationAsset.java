package TradingPlatform;

import java.io.IOException;
import java.util.ArrayList;

/**
 * OrganisationAssets stores an asset owned by each OrganisationalUnit and records unique ID for this pair
 */
public class OrganisationAsset {
    private int organisationAssetID;
    private int organisationUnitID;
    private String assetType;
    private int quantity;
    private static NetworkManager networkManager = ClientApp.networkManager;

    // GUI OrganisationAsset Constructor
    public OrganisationAsset(int organisationUnitID, String assetType, int quantity){
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
        return response.getDoubleString();
    }


    public int getOrganisationAssetID() {
        return organisationAssetID;
    }

    // Gets the orgAssetId for a given unit and asset type, or -1 on error or if the unit doesn't have any of that asset
    public static int getOrganisationAssetID(OrganisationalUnit orgUnit, AssetType AssetType){
        try {
            Request response = NetworkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetId",
                    new String[]{ Integer.toString(orgUnit.getID()), Integer.toString(AssetType.getAssetId()) } );
            // String[id] response
            return Integer.parseInt(response.getArguments()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getOrganisationUnitID() {
        return organisationUnitID;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the quantity of the given organisation asset
     * @param orgAssetId the id of the organisation asset
     * @return the quantity of this asset that the org asset's unit has, or -1 on error
     */
    public static int getQuantity(int orgAssetId){
        try {
            Request response = NetworkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetQuantity",
                    new String[]{ Integer.toString(orgAssetId) });
            return Integer.parseInt(response.getArguments()[0]);
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    //public AssetType getAssetType() { return assetType;  }
}
