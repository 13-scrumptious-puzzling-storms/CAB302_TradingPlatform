package TradingPlatform;

import java.io.IOException;

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
    public String[][] getOrganisationalUnitAssetTable(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetsQuantity", new String[] {String.valueOf(orgID)});
        String test = "[[\"Hello\", \"hi\"], [\"1\", \"5\", \"7\"], [\"liam\", \"is\", \"old now\"]]";
        //for (int i = 0; i < ) {

        //}
        //return response.getArguments()[0];
        System.out.println(response.getArguments()[0]);
        return null;
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
