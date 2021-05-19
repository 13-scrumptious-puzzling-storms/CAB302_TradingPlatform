package TradingPlatform;

/**
 * OrganisationAssets stores an asset owned by each OrganisationalUnit and records unique ID for this pair
 */
public class OrganisationAsset {
    private int organisationAssetID;
    private int organisationUnitID;
    private AssetType assetType;
    private int quantity;

    // GUI OrganisationAsset Constructor
    public OrganisationAsset(int organisationAssetID, int organisationUnitID, AssetType assetType, int quantity){
        this.organisationAssetID = organisationAssetID;
        this.organisationUnitID = organisationUnitID;
        this.assetType = assetType;
        this.quantity = quantity;
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
