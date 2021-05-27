package TradingPlatform.JDBCDataSources;

import TradingPlatform.AccountType;
import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;

public class JDBCUserDataSource implements UserDataSource {

    private static final String GET_USER = "SELECT * FROM User WHERE userId=?";
    private static final String GET_USERID_FROM_USERNAME_PASSWORD = "SELECT userId FROM User WHERE username=? and password=?";

    private PreparedStatement getUser;
    private static PreparedStatement getUserIdFromUsernamePassword;

    private Connection connection;

    private int userId;
    private String username;
    private AccountType accountType;
    private OrganisationalUnit organisationalUnit;

    public JDBCUserDataSource(int userId, Connection connection){
        this.connection = connection;
        this.userId = userId;

        try {
            // Preparing Statements
            getUser = connection.prepareStatement(GET_USER);

            // Getting the user's data
            getUser.setInt(1, userId);
            ResultSet rs = getUser.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
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
            getUserIdFromUsernamePassword.setString(1, username);
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
        return false;
    }
}
