package TradingPlatform;

import java.io.IOException;
import java.util.ArrayList;

/**
 * OrganisationAssets stores an asset owned by each OrganisationalUnit
 * and records unique ID for this pair. Makes request from the database
 * regarding organisation asset information
 */
public class OrganisationAsset {
    private int organisationAssetID;
    private final int organisationUnitID;
    private final String assetType;
    private final int quantity;
    private static final NetworkManager networkManager = ClientApp.networkManager;

    /**
     * Organisation Asset constructor with given organisational unit ID, asset type, and quantity
     * @param organisationUnitID organisational unit ID belonging to organisation unit which owns the asset
     * @param assetType asset type that the organisation unit owns
     * @param quantity quantity of assets that the organisational unit owns
     */
    public OrganisationAsset(int organisationUnitID, String assetType, int quantity){
        this.organisationUnitID = organisationUnitID;
        this.assetType = assetType;
        this.quantity = quantity;
    }

    /**
     * Creates new empty instance of Organisational Asset to be filled with
     * information from the database
     */
    public OrganisationAsset() {
            this.organisationUnitID = 0;
            this.assetType = "";
            this.quantity = 0;
    }


    /**
     * Creates request to the database to retrieve contents of organisation's assets
     * table - organisation's assets name and quantity
     * @param orgID organisational unit ID
     * @return Double string array containing organisation assset name and quantity
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getOrganisationalUnitAssetTable(int orgID) {
        try {
            Request response = networkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetsAndQuantity", new String[]{String.valueOf(orgID)});
            return response.getDoubleString();
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates request to the database to update the quantity of the organisation's assets
     * @param orgAssetID organisationAsset ID
     * @param assetQuantity new quantity to update the amount of assets the organisation owns
     */
    public static void updateOrganisationalUnitAssetQuantity(int orgAssetID, int assetQuantity){
        try{
            NetworkManager.SendRequest("JDBCOrganisationalAsset", "updateOrganisationAssetsQuantity",
                    new String[] {String.valueOf(orgAssetID), String.valueOf(assetQuantity)});
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves the organisationAssetID for a given organisation unit and asset type.
     * @param orgUnit
     * @param AssetType
     * @return  organisationAssetID for given organisation unit and asset typ
     *          (-1 on an error or if the organisation doesn't have any assets)
     */
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

    /**
     * Returns quantity of the instance of the organisationAsset
     * @return quantity of the instance of the organisationAsset
     */
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

    /**
     * creates new organisationAsset using organisationID, assetTypeID, and quantity
     * @param orgUnitID organisationalUnit ID
     * @param assetTypeID AssetType ID
     * @param quantity number of assets to be added
     * @return organisationAssetID
     */
    public static int addOrganisationAsset(int orgUnitID, int assetTypeID, int quantity){
        try {
            Request response = NetworkManager.GetResponse("JDBCOrganisationalAsset", "addOrganisationAsset",
                    new String[]{ Integer.toString(orgUnitID), Integer.toString(assetTypeID), Integer.toString(quantity) });
            return Integer.parseInt(response.getArguments()[0]);
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

}
