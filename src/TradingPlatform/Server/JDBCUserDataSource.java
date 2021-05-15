package TradingPlatform.Server;

import TradingPlatform.AccountType;
import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationalUnit;

import java.sql.*;

public class JDBCUserDataSource implements UserDataSource {

//    private static final String INSERT_ORGANISATIONALUNIT = "INSERT INTO organisationalunit (name, credits) VALUES (?, ?);";
    private static final String GET_USERNAME = "SELECT username FROM User WHERE userId=?";

//    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement getUsername;

    private Connection connection;

    private int userId;

    public JDBCUserDataSource(int userId, Connection connection){
        this.connection = connection;
        this.userId = userId;

        try {
            Statement st = connection.createStatement();

            // Preparing Statements
//            addOrganisationalUnit = connection.prepareStatement(INSERT_ORGANISATIONALUNIT);
            getUsername = connection.prepareStatement(GET_USERNAME);

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
                return rs.getString(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public AccountType getAccountType() {
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
