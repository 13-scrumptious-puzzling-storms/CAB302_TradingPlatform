package TradingPlatform.Server;

import TradingPlatform.Interfaces.OrganisationalUnitSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;
import java.util.HashMap;

public class JDBCOrganisationalUnit implements OrganisationalUnitSource {
    private static final String INSERT_ORGANISATIONALUNIT = "INSERT INTO organisationalunit (name, credits) VALUES (?, ?);";
    private static final String GET_ORGANISATIONALUNITNAME = "SELECT name FROM organisationalunit WHERE organisationalunitid=?";
    private static final String GET_ORGANISATIONALUNIT = "SELECT * FROM organisationalunit WHERE organisationalunitid=?";

    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement getOrganisationalUnit;
    private PreparedStatement getOrganisationalUnitName;


    private Connection connection;

    private int OrgUnitId;

    public JDBCOrganisationalUnit(int OrgUnitId, Connection connection){
        this.connection = connection;
        this.OrgUnitId = OrgUnitId;

        try {
            // Preparing Statements
//          addOrganisationalUnit = connection.prepareStatement(INSERT_ORGANISATIONALUNIT);
            getOrganisationalUnit = connection.prepareStatement(GET_ORGANISATIONALUNIT);
            getOrganisationalUnitName = connection.prepareStatement(GET_ORGANISATIONALUNITNAME);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getOrganisationalUnitName() {
        try {
            getOrganisationalUnitName.clearParameters();
            getOrganisationalUnitName.setInt(1, OrgUnitId);
            ResultSet rs = getOrganisationalUnitName.executeQuery();

            if (rs.next()) {
                var orgName = rs.getString("name");
                return rs.getString(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
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

