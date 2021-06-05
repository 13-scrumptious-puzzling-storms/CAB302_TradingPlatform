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

    //table column headings
    private static final String NAME_HEADING = "name";
    private static final String CREDITS_HEADING = "credits";
    private static final String ID_HEADING = "OrganisationUnitID";

    //prepared statements
    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement updateOrganisationalUnitCredits;
    private PreparedStatement getOrganisationalUnitName;
    private PreparedStatement getOrganisationalUnitCredits;
    private PreparedStatement getOrganisationalUnit;
    private PreparedStatement getNewOrganisationalUnitID;
    private PreparedStatement getAllOrganisationUnitNames;
    private PreparedStatement getNumOrganisationalUnitsWithName;

    //instance of the connection to the database
    private Connection connection;

    /**
     * Creates instance of the prepared statements for the connection to the database
     * @param connection
     */
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

    /**
     * Creates new organisationalUnit using organisation name and number of credits
     * returns organisationalUnit ID
     * @param orgName organisation's name
     * @param orgCredits number of credits belonging to the organisational unit
     * @return organisationalUnit ID
     */
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

    /**
     * retrieves name of organisational unit with the given ID
     * @param orgUnitId organisationalUnit ID
     * @return name of the organisation
     */
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

    /**
     * retrieves number of credits belonging to the organisational unit
     * @param OrgUnitId organisationalUnit ID
     * @return number of credits belonging to the organisational unit
     */
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

    /**
     * retrieves constructed organisational unit with all details given
     * the organisationalUnit ID
     * @param orgUnitId organisationalUnit ID
     * @return OrganisationalUnit of the given organisationalUnitID
     */
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

    /**
     * updates the number of credits of a given organisational unit
     * @param OrgUnitID organisationalUnit ID
     * @param updatedCredits new number of credits owned by given organisationalUnit
     * @return returns true if successful, false otherwise
     */
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


    /**
     * Returns the names and ID of the organisationalUnit. For IT admin when editing
     * users and organisationalUnits
     * @return double array of organisationalUnit names and IDs
     */
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

}

