package TradingPlatform.JDBCDataSources;

import TradingPlatform.AccountType;
import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;

public class JDBCUserDataSource implements UserDataSource {

    private static final String GET_USER = "SELECT * FROM User WHERE userId=?";
    private static final String GET_USERID_FROM_USERNAME_PASSWORD = "SELECT userId FROM User WHERE username=? and password=?";
    private static final String SET_USER_PASSWORD = "UPDATE User SET password=? WHERE userId=?";
    private static final String INSERT_USER = "INSERT INTO USER (username, password, organisationUnitId, userRol) VALUES (?, ?, ?, ?)";

    private PreparedStatement getUser;
    private PreparedStatement setUserPassword;
    private static PreparedStatement getUserIdFromUsernamePassword;
    private static PreparedStatement insertUser;

    private Connection connection;

    private int userId;
    private String username;
    private String password;
    private AccountType accountType;
    private OrganisationalUnit organisationalUnit;

    public JDBCUserDataSource(int userId, Connection connection){
        this.connection = connection;
        this.userId = userId;

        try {
            // Preparing Statements
            getUser = connection.prepareStatement(GET_USER);
            setUserPassword = connection.prepareStatement(SET_USER_PASSWORD);

            // Getting the user's data
            getUser.setInt(1, userId);
            ResultSet rs = getUser.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
                password = rs.getString("password");
                accountType = AccountType.getType(rs.getInt("userRole"));
                int orgId = rs.getInt("organisationUnitId");
                organisationalUnit = new JDBCOrganisationalUnit(connection).getOrganisationalUnit(orgId);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Tries to find the user matching the username and password
     * @return the userId of the user, or -1 if not found
     */
    public static int getUserId(String username, String password, Connection connection){
        int userId = -1;
        try {
            getUserIdFromUsernamePassword = connection.prepareStatement(GET_USERID_FROM_USERNAME_PASSWORD);
            getUserIdFromUsernamePassword.setString(1, username.toLowerCase());
            getUserIdFromUsernamePassword.setString(2, password);
            var rs = getUserIdFromUsernamePassword.executeQuery();
            if (rs.next()){
                userId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userId;
    }

    /**
     * Adds a new user to the database
     * @return the userId of the user, or -1 if not found
     */
    public static void addUser(String username, String password, AccountType AccountType, int OrganisationUnitId, Connection connection){
        try {
            insertUser = connection.prepareStatement(INSERT_USER);
            insertUser.setString(1, username.toLowerCase());
            insertUser.setString(2, password);
            insertUser.setInt(3, AccountType.getValue());
            insertUser.setInt(4, OrganisationUnitId);
            int numRecords = insertUser.executeUpdate();
            if (numRecords == 0){
                throw new SQLException("Unable to insert new user into database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public OrganisationalUnit getOrganisationalUnit() {
        return organisationalUnit;
    }

    @Override
    public boolean ChangePassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(password)){
            try {
                password = newPassword;
                setUserPassword.clearParameters();
                setUserPassword.setString(1, password);
                setUserPassword.setInt(2, userId);
                int numRecords = setUserPassword.executeUpdate();
                if (numRecords == 0){
                    throw new SQLException("Unable to update password for user " + userId + ".");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }
}
