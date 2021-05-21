package TradingPlatform.JDBCDataSources;

import TradingPlatform.AccountType;
import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;

public class JDBCUserDataSource implements UserDataSource {

    private static final String GET_USER = "SELECT * FROM User WHERE userId=?";
    private static final String GET_USERNAME = "SELECT username FROM User WHERE userId=?";
    private static final String GET_ACCOUNT_TYPE = "SELECT userRole FROM User WHERE userId=?";
    private static final String GET_ORGANISATIONAL_UNIT = "SELECT userRole FROM User WHERE userId=?";

    private PreparedStatement getUser;
    private PreparedStatement getUsername;
    private PreparedStatement getAccountType;
    private PreparedStatement getOrganisationalUnit;

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

            getUser.setInt(1, userId);
            ResultSet rs = getUser.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
                accountType = AccountType.getType(rs.getInt("userRole"));
                int orgId = rs.getInt("organisationUnitId");
                organisationalUnit = new JDBCOrganisationalUnit( connection).getOrganisationalUnit(orgId);
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
        return false;
    }
}
