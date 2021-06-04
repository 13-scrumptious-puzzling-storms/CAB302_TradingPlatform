package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.OrganisationalUnitSource;
import TradingPlatform.OrganisationalUnit;

//import java.lang.constant.Constable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class JDBCOrganisationalUnit implements OrganisationalUnitSource {
    private static final String INSERT_ORGANISATIONALUNIT = "INSERT INTO OrganisationUnit (name, credits) VALUES (?, ?);";
    private static final String UPDATE_ORGANISATIONALUNIT_CREDITS = "UPDATE OrganisationUnit SET credits=? WHERE  OrganisationUnitID=?;";
    private static final String GET_ORGANISATIONALUNIT_NAME = "SELECT name FROM OrganisationUnit WHERE OrganisationUnitID=?";
    private static final String GET_ORGANISATIONALUNIT_CREDITS = "SELECT credits FROM OrganisationUnit WHERE OrganisationUnitID=?";
    private static final String GET_ORGANISATIONALUNIT = "SELECT * FROM OrganisationUnit WHERE OrganisationUnitID=?";
    private static final String GET_NEW_ORGANISATIONALUNIT_ID = "SELECT * FROM OrganisationUnit WHERE name=?";
    private static final String ORGID_HEADING = "OrganisationUnitID";

    private static final String GET_ALL_ORGANISATIONALUNIT_NAMES = "SELECT OrganisationUnitID, name, credits FROM OrganisationUnit";
    private static final String GET_NUM_ORGANISATIONALUNITS_WITH_NAME = "SELECT COUNT(*) FROM OrganisationUnit where name=?";

    private static final String NAME_HEADING = "name";
    private static final String CREDITS_HEADING = "credits";
    private static final String ID_HEADING = "OrganisationUnitID";


    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement updateOrganisationalUnitCredits;
    private PreparedStatement getOrganisationalUnitName;
    private PreparedStatement getOrganisationalUnitCredits;
    private PreparedStatement getOrganisationalUnit;
    private PreparedStatement getNewOrganisationalUnitID;
    private PreparedStatement getAllOrganisationUnitNames;
    private PreparedStatement getNumOrganisationalUnitsWithName;



    private Connection connection;


    public JDBCOrganisationalUnit(Connection connection){
        this.connection = connection;

        try {
            // Preparing Statements
            addOrganisationalUnit = connection.prepareStatement(INSERT_ORGANISATIONALUNIT, Statement.RETURN_GENERATED_KEYS);
            updateOrganisationalUnitCredits = connection.prepareStatement(UPDATE_ORGANISATIONALUNIT_CREDITS);
            getOrganisationalUnit = connection.prepareStatement(GET_ORGANISATIONALUNIT);
            getOrganisationalUnitName = connection.prepareStatement(GET_ORGANISATIONALUNIT_NAME);
            getOrganisationalUnitCredits = connection.prepareStatement(GET_ORGANISATIONALUNIT_CREDITS);
            getNewOrganisationalUnitID = connection.prepareStatement(GET_NEW_ORGANISATIONALUNIT_ID);
            getAllOrganisationUnitNames = connection.prepareStatement(GET_ALL_ORGANISATIONALUNIT_NAMES);
            getNumOrganisationalUnitsWithName = connection.prepareStatement(GET_NUM_ORGANISATIONALUNITS_WITH_NAME);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int addOrganisationalUnit(String orgName, int orgCredits) {
        try {
            // Check that there are no orgUnits with the given name
            getNumOrganisationalUnitsWithName.setString(1, orgName);
            ResultSet rs = getNumOrganisationalUnitsWithName.getResultSet();
            if (rs.next())
                if (rs.getInt(1) != 0)
                    return -1; // There is already an org with the given name

            addOrganisationalUnit.clearParameters();
            addOrganisationalUnit.setString(1, orgName);
            addOrganisationalUnit.setInt(2, orgCredits);
            int affectedRows = addOrganisationalUnit.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("JDBCOrganisationalUnit unable to retrieve ID: no affected rows");
            }
            ResultSet generatedKeys = addOrganisationalUnit.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("JDBCOrganisationalUnit unable to retrieve ID");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public String getOrganisationalUnitName(int orgUnitId) {
        try {
            getOrganisationalUnitName.clearParameters();
            getOrganisationalUnitName.setInt(1, orgUnitId);
            ResultSet rs = getOrganisationalUnitName.executeQuery();

            if (rs.next()) {
                String orgName = rs.getString(NAME_HEADING);
                return orgName;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getOrganisationalUnitCredits(int OrgUnitId) {
        try {
            getOrganisationalUnitCredits.clearParameters();
            getOrganisationalUnitCredits.setInt(1, OrgUnitId);
            ResultSet rs = getOrganisationalUnitCredits.executeQuery();

            if (rs.next()) {
                int orgCredits = rs.getInt(CREDITS_HEADING);
                return orgCredits;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public OrganisationalUnit getOrganisationalUnit(int orgUnitId) {
        try {
            getOrganisationalUnit.clearParameters();
            getOrganisationalUnit.setInt(1, orgUnitId);
            ResultSet rs = getOrganisationalUnit.executeQuery();

            if (rs.next()) {
                var orgUnit = new OrganisationalUnit();
                orgUnit.setId(orgUnitId);
                orgUnit.setName(rs.getString(NAME_HEADING));
                orgUnit.setCredits(rs.getInt(CREDITS_HEADING));
                return orgUnit;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public Boolean UpdateOrganisationalUnitCredits(int OrgUnitID, int updatedCredits){
        try{
            updateOrganisationalUnitCredits.clearParameters();
            updateOrganisationalUnitCredits.setInt(1, updatedCredits);
            updateOrganisationalUnitCredits.setInt(2, OrgUnitID);
            updateOrganisationalUnitCredits.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // Returns the names of the organisation units, as well as their id's
    // This is for the IT Admin when editing org Units / adding users
    public String[][] getAllOrganisationalUnits(){
        ArrayList<String[]> orgNames = new ArrayList<>();
        try {
            ResultSet rs = getAllOrganisationUnitNames.executeQuery();
            while (rs.next()){
                // Add the name and id to the list
                orgNames.add(new String[]{
                        Integer.toString(rs.getInt(ID_HEADING)),
                        rs.getString(NAME_HEADING),
                        Integer.toString(rs.getInt(CREDITS_HEADING))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orgNames.toArray(new String[orgNames.size()][]);
    }


    //WHAT TO DO WITH THESE!?
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

