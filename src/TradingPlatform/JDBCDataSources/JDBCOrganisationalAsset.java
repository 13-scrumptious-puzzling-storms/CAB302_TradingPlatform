package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.OrganisationalAssetSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Retrieves requested information from the OrganisationAsset table of the database
 */
public class JDBCOrganisationalAsset implements OrganisationalAssetSource {
    private static final String INSERT_ORGANISATIONASSET = "INSERT INTO OrganisationAsset (organisationUnitID, assetTypeID, Quantity) VALUES (?, ?, ?);";
    private static final String UPDATE_ORGANISATIONASSET_QUANTITY = "UPDATE OrganisationAsset SET Quantity=? WHERE  OrganisationAssetID=?;";
    private static final String GET_ORGANISATIONASSET_ORGUNITID = "SELECT organisationUnitID FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORGANISATIONASSET_ASSETTYPEID = "SELECT assetTypeID FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORGANISATIONASSET_QUANTITY = "SELECT Quantity FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORG_ASSETS_TABLE = "SELECT o.*, name FROM organisationAsset AS o\n" +
            "LEFT JOIN assetType as a ON o.assetTypeId = a.assetTypeId\n"+
            "WHERE o.organisationUnitId = ?";
    private static final String GET_ORG_ASSETS_TABLE_COUNT = "SELECT count(name) as num, name FROM organisationAsset AS o\n" +
            "LEFT JOIN assetType as a ON o.assetTypeId = a.assetTypeId\n"+
            "WHERE o.organisationUnitId = ?";
    private static final String GET_ORGANISATIONASSET_ID = "SELECT OrganisationAssetID FROM OrganisationAsset WHERE organisationUnitID=? and assetTypeID=?";

    //table column headings
    private static final String ORGID_HEADING = "organisationUnitID";
    private static final String ASSETTYPEID_HEADING = "assetTypeID";
    private static final String QUANTITY_HEADING = "Quantity";
    private static final String NAME_HEADING = "name";
    private static final String ASSET_ID_HEADING = "assetTypeID";

    //Prepared statements
    private PreparedStatement addOrganisationAsset;
    private PreparedStatement updateOrganisationAssetQuantity;
    private PreparedStatement getOrganisationAssetOrgUnitID;
    private PreparedStatement getOrganisationAssetTypeID;
    private PreparedStatement getOrganisationAssetQuantity;
    private PreparedStatement getOrganisationAssetsTable;
    private PreparedStatement getOrgAssetsTableCount;
    private PreparedStatement getOrgAssetId;

    //connections instance
    private Connection connection;

    /**
     * Creates instance of the prepared statements for the connection to the database
     * @param connection
     */
    public JDBCOrganisationalAsset(Connection connection){
        this.connection = connection;

        try {
            // Preparing Statements
            addOrganisationAsset = connection.prepareStatement(INSERT_ORGANISATIONASSET);
            updateOrganisationAssetQuantity = connection.prepareStatement(UPDATE_ORGANISATIONASSET_QUANTITY);
            getOrganisationAssetOrgUnitID = connection.prepareStatement(GET_ORGANISATIONASSET_ORGUNITID);
            getOrganisationAssetTypeID = connection.prepareStatement(GET_ORGANISATIONASSET_ASSETTYPEID);
            getOrganisationAssetQuantity = connection.prepareStatement(GET_ORGANISATIONASSET_QUANTITY);
            getOrganisationAssetsTable = connection.prepareStatement(GET_ORG_ASSETS_TABLE);
            getOrgAssetsTableCount = connection.prepareStatement(GET_ORG_ASSETS_TABLE_COUNT);
            getOrgAssetId = connection.prepareStatement(GET_ORGANISATIONASSET_ID);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves organisationalUnitID of the organisationAsset
     * @param orgAssetId organisationAssetID
     * @return organisationalUnitID
     */
    public int getOrganisationAssetOrgUnitID(int orgAssetId) {
        try {
            getOrganisationAssetOrgUnitID.clearParameters();
            getOrganisationAssetOrgUnitID.setInt(1, orgAssetId);
            ResultSet rs = getOrganisationAssetOrgUnitID.executeQuery();

            if (rs.next()) {
                int orgUnitID = rs.getInt(ORGID_HEADING);
                return orgUnitID;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves quantity of the organisationAsset
     * @param orgAssetId organisationAsset ID
     * @return quantity of the organisationAssetID
     */
    public int getOrganisationAssetQuantity(int orgAssetId) {
        try {
            getOrganisationAssetQuantity.clearParameters();
            getOrganisationAssetQuantity.setInt(1, orgAssetId);
            ResultSet rs = getOrganisationAssetQuantity.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt(QUANTITY_HEADING);
                return quantity;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves the asset ID that belongs to
     * the given organisationAssetID
     * @param orgAssetId organisationAsset ID
     * @return asset ID of the given organisationAssetID
     */
    public int getOrganisationAssetTypeID(int orgAssetId) {
        try {
            getOrganisationAssetTypeID.clearParameters();
            getOrganisationAssetTypeID.setInt(1, orgAssetId);
            ResultSet rs = getOrganisationAssetTypeID.executeQuery();

            if (rs.next()) {
                int orgUnitID = rs.getInt(ASSETTYPEID_HEADING);
                return orgUnitID;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves the organisation's asset names and quantity
     * @param orgUnitId organisationUnit ID
     * @return organisationUnits assets and quantities in double string array
     */
    public String[][] getOrganisationAssetsAndQuantity(int orgUnitId){
        try {
            getOrganisationAssetsTable.clearParameters();
            getOrganisationAssetsTable.setInt(1, orgUnitId);
            ResultSet rs = getOrganisationAssetsTable.executeQuery();

            getOrgAssetsTableCount.clearParameters();
            getOrgAssetsTableCount.setInt(1, orgUnitId);
            ResultSet num = getOrgAssetsTableCount.executeQuery();
            int count;
            if (num.next()) {
                count = num.getInt("num");
            }else {
                count = 0;
            }

            String[][] assets = new String[count][];
            int i = 0;
            while (rs.next()) {
                String[] ass = new String[3];
                ass[0] = Integer.toString(rs.getInt(ASSET_ID_HEADING));
                ass[1] = rs.getString(NAME_HEADING);
                ass[2] = String.valueOf(rs.getInt(QUANTITY_HEADING));
                assets[i] = ass;
                i++;
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Adds new organisationAsset to the database using organisationUnitID, assetTypeId, and quantity
     * @param orgUnitID organisationUnit ID to add organisationAsset to
     * @param assetTypeID Asset type ID of asset that is to be added
     * @param quantity quantity to add organisationAsset to
     * @return ID of the organisation asset
     */
    public int addOrganisationAsset(int orgUnitID, int assetTypeID, int quantity) {
        try {
            addOrganisationAsset.clearParameters();
            addOrganisationAsset.setInt(1, orgUnitID);
            addOrganisationAsset.setInt(2, assetTypeID);
            addOrganisationAsset.setInt(3, quantity);

            int affectedRows = addOrganisationAsset.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("JDBCOrganisationAsset unable to retrieve ID: no affected rows");
            }
            ResultSet generatedKeys = addOrganisationAsset.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("JDBCOrganisationAsset unable to retrieve ID");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    /**
     * Updates the quantity of an organisation asset with given ID
     * @param orgAssetID organisationAsset ID to be updated
     * @param quantity new quantity of the organisationAsset
     */
    public void UpdateOrganisationAssetQuantity(int orgAssetID, int quantity){
        synchronized (JDBCThreadLock.UpdateDbLock) {
            try {
                updateOrganisationAssetQuantity.clearParameters();
                updateOrganisationAssetQuantity.setInt(1, quantity);
                updateOrganisationAssetQuantity.setInt(2, orgAssetID);
                updateOrganisationAssetQuantity.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }


    /**
     * Returns the organisationAssetID for a given organisationalUnit and AssetType
     * @param orgUnitId organisationUnit ID
     * @param assetTypeId assetTypeID
     * @return organisationAssetID (-1 on error)
     */
    public int getOrganisationAssetId(int orgUnitId, int assetTypeId){
        int assetId = -1;
        try {
            getOrgAssetId.clearParameters();
            getOrgAssetId.setInt(1, orgUnitId);
            getOrgAssetId.setInt(2, assetTypeId);
            var rs = getOrgAssetId.executeQuery();

            if (rs.next()){
                assetId = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assetId;
    }
}
