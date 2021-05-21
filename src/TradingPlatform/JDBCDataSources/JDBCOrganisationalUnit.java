package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.OrganisationalUnitSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;
import java.util.HashMap;

public class JDBCOrganisationalUnit implements OrganisationalUnitSource {
    private static final String INSERT_ORGANISATIONALUNIT = "INSERT INTO OrganisationUnit (name, credits) VALUES (?, ?);";
    private static final String UPDATE_ORGANISATIONALUNIT_CREDITS = "UPDATE OrganisationUnit SET credits=? WHERE  OrganisationUnitID=?;";
    private static final String GET_ORGANISATIONALUNIT_NAME = "SELECT name FROM OrganisationUnit WHERE OrganisationUnitID=?";
    private static final String GET_ORGANISATIONALUNIT = "SELECT * FROM OrganisationUnit WHERE OrganisationUnitID=?";

    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement updateOrganisationalUnitCredits;
    private PreparedStatement getOrganisationalUnit;
    private PreparedStatement getOrganisationalUnitName;


    private Connection connection;

    private int OrgUnitId;

    public JDBCOrganisationalUnit(int OrgUnitId, Connection connection){
        this.connection = connection;
        this.OrgUnitId = OrgUnitId;

        try {
            // Preparing Statements
            addOrganisationalUnit = connection.prepareStatement(INSERT_ORGANISATIONALUNIT);
            updateOrganisationalUnitCredits = connection.prepareStatement(UPDATE_ORGANISATIONALUNIT_CREDITS);
            getOrganisationalUnit = connection.prepareStatement(GET_ORGANISATIONALUNIT);
            getOrganisationalUnitName = connection.prepareStatement(GET_ORGANISATIONALUNIT_NAME);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getOrganisationalUnitName() {
        try {
            getOrganisationalUnitName.clearParameters();
            getOrganisationalUnitName.setInt(1, OrgUnitId);
//            getOrganisationalUnitName.executeUpdate();
            ResultSet rs = getOrganisationalUnitName.executeQuery();

            if (rs.next()) {
                String orgName = rs.getString("name");
                return orgName;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void UpdateOrganisationalunitCredits(int orgUnitId, int updatedCredits){
        try{
            updateOrganisationalUnitCredits.clearParameters();
            updateOrganisationalUnitCredits.setInt(1, updatedCredits);
            updateOrganisationalUnitCredits.setInt(2, orgUnitId);
            updateOrganisationalUnitCredits.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public OrganisationalUnit getOrganisationalUnit() {
        try {
            getOrganisationalUnit.clearParameters();
            getOrganisationalUnit.setInt(1, OrgUnitId);
            ResultSet rs = getOrganisationalUnit.executeQuery();

            if (rs.next()) {
                var orgUnit = new OrganisationalUnit();
                orgUnit.setId(OrgUnitId);
                orgUnit.setName(rs.getString("name"));
                orgUnit.setCredits(rs.getInt("credits"));
                return orgUnit;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public void addOrganisationalUnit(int orgunitID) {
        try {
            addOrganisationalUnit.clearParameters();
            //addOrganisationalUnit.setInt(1, OrganisationalUnit.getName(orgunitID));
            //addOrganisationalUnit.setInt(1, orgunit.getName());

            ResultSet rs = getOrganisationalUnit.executeQuery();

            if (rs.next()) {
                var orgUnit = new OrganisationalUnit();
                orgUnit.setId(OrgUnitId);
                orgUnit.setName(rs.getString("name"));
                orgUnit.setCredits(rs.getInt("credits"));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @Override
    public int assignID() {
        return 0;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void setId(int id) {
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setCredits(int credits) {

    }

    @Override
    public void addAsset(int organisationID, Object asset, int quantity) {

    }

    @Override
    public HashMap getAssets(int organisationID) {
        return null;
    }

    @Override
    public HashMap getCurrentBuyOrders(int organisationID) {
        return null;
    }

    @Override
    public HashMap getCurrentSellOrders(int organisationID) {
        return null;
    }
}

