package TradingPlatform.JDBCDataSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCOrganisationalAsset {
    private static final String INSERT_ORGANISATIONASSET = "INSERT INTO OrganisationAsset (organisationUnitID, assetTypeID, Quantity) VALUES (?, ?, ?);";
    private static final String UPDATE_ORGANISATIONASSET_QUANTITY = "UPDATE OrganisationAsset SET Quantity=? WHERE  OrganisationAssetID=?;";
    private static final String GET_ORGANISATIONASSET_ORGID = "SELECT organisationUnitID FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORGANISATIONASSET_ASSETTYPEID = "SELECT assetTypeID FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORGANISATIONASSET_QUANTITY = "SELECT Quantity FROM OrganisationAsset WHERE OrganisationAssetID=?";
    private static final String GET_ORGANISATIONASSET_ID = "SELECT organisationAssetID FROM OrganisationAsset WHERE organisationUnitID=? AND assetTypeID=?";

    private static final String ORGASSETID_HEADING = "organisationAssetID";
    private static final String ORGID_HEADING = "organisationUnitID";
    private static final String ASSETTYPEID_HEADING = "assetTypeID";
    private static final String QUANTITY_HEADING = "Quantity";

    private PreparedStatement addOrganisationAsset;
    private PreparedStatement updateOrganisationAssetQuantity;
    private PreparedStatement getOrganisationAssetOrgID;
    private PreparedStatement getOrganisationAssetTypeID;
    private PreparedStatement getOrganisationAssetQuantity;
    private PreparedStatement getOrganisationAssetID;


    private Connection connection;


    public JDBCOrganisationalAsset(Connection connection){
        this.connection = connection;

        try {
            // Preparing Statements
            addOrganisationAsset = connection.prepareStatement(INSERT_ORGANISATIONASSET);
            updateOrganisationAssetQuantity = connection.prepareStatement(UPDATE_ORGANISATIONASSET_QUANTITY);
            getOrganisationAssetOrgID = connection.prepareStatement(GET_ORGANISATIONASSET_ORGID);
            getOrganisationAssetTypeID = connection.prepareStatement(GET_ORGANISATIONASSET_ASSETTYPEID);
            getOrganisationAssetQuantity = connection.prepareStatement(GET_ORGANISATIONASSET_QUANTITY);
            getOrganisationAssetID = connection.prepareStatement(GET_ORGANISATIONASSET_ID);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getOrganisationAssetOrgID(int orgAssetId) {
        try {
            getOrganisationAssetOrgID.clearParameters();
            getOrganisationAssetOrgID.setInt(1, orgAssetId);
            ResultSet rs = getOrganisationAssetOrgID.executeQuery();

            if (rs.next()) {
                int orgUnitID = rs.getInt(ORGID_HEADING);
                return orgUnitID;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

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

    public int addOrganisationAsset(int orgUnitID, int assetTypeID, int quantity) {
        try {
            addOrganisationAsset.clearParameters();
            addOrganisationAsset.setInt(1, orgUnitID);
            addOrganisationAsset.setInt(2, assetTypeID);
            addOrganisationAsset.setInt(3, quantity);
            addOrganisationAsset.executeUpdate();
            getOrganisationAssetID.clearParameters();
            getOrganisationAssetID.setInt(1, orgUnitID);
            getOrganisationAssetID.setInt(2, assetTypeID);
            ResultSet rs = getOrganisationAssetID.executeQuery();
            if (rs.next()) {
                return rs.getInt(ORGASSETID_HEADING);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void UpdateOrganisationAssetQuantity(int orgAssetID, int quantity){
        try{
            updateOrganisationAssetQuantity.clearParameters();
            updateOrganisationAssetQuantity.setInt(1, quantity);
            updateOrganisationAssetQuantity.setInt(2, orgAssetID);
            updateOrganisationAssetQuantity.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
