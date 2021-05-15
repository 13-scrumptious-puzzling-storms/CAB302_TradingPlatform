package TradingPlatform.Server;

import TradingPlatform.AccountType;
import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;

public class JDBCUserDataSource implements UserDataSource {

    private static final String GET_USERNAME = "SELECT username FROM User WHERE userId=?";
    private static final String GET_ACOUNTTYPE = "SELECT userRole FROM User WHERE userId=?";

    private PreparedStatement getUsername;
    private PreparedStatement getAccountType;

    private Connection connection;

    private int userId;

    public JDBCUserDataSource(int userId, Connection connection){
        this.connection = connection;
        this.userId = userId;

        try {
            Statement st = connection.createStatement();

            // Preparing Statements
            getUsername = connection.prepareStatement(GET_USERNAME);
            getAccountType = connection.prepareStatement(GET_ACOUNTTYPE);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getUsername() {
        try {
            getUsername.clearParameters();
            getUsername.setInt(1, userId);
            ResultSet rs = getUsername.executeQuery();

            if (rs.next())
                return rs.getString("username");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public AccountType getAccountType() {
        try {
            getAccountType.clearParameters();
            getAccountType.setInt(1, userId);
            ResultSet rs = getAccountType.executeQuery();

            if (rs.next()) {
                int typeInt = rs.getInt("userRole");
                return AccountType.getType(typeInt);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public OrganisationalUnit getOrganisationalUnit() {
        return null;
    }

    @Override
    public boolean ChangePassword(String currentPassword, String newPassword) {
        return false;
    }
}
