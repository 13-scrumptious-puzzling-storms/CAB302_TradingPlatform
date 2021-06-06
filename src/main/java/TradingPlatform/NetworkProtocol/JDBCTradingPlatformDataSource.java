package TradingPlatform.NetworkProtocol;
import TradingPlatform.OrganisationalUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class JDBCTradingPlatformDataSource implements TradingPlatformDataSource {

    private static final String INSERT_ORGANISATIONALUNIT = "INSERT INTO organisationalunit (name, credits) VALUES (?, ?);";
    private static final String GET_ORGANISATIONALUNIT = "SELECT * FROM organisationalunit WHERE id=?";

    private PreparedStatement addOrganisationalUnit;
    private PreparedStatement getOrganisationalUnit;

    private Connection connection;

    /**
     * Constructor connects to connection instance
     * and prepares executable statements.
     */
    public JDBCTradingPlatformDataSource() {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();

            // Preparing Statements
            addOrganisationalUnit = connection.prepareStatement(INSERT_ORGANISATIONALUNIT);
            getOrganisationalUnit = connection.prepareStatement(GET_ORGANISATIONALUNIT);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see TradingPlatform.NetworkProtocol.TradingPlatformDataSource#addOrganisationalUnit(TradingPlatform.OrganisationalUnit)
     */
    public void addOrganisationalUnit(OrganisationalUnit orgUnit) {

    }

    /**
     * @see TradingPlatform.NetworkProtocol.TradingPlatformDataSource#getOrganisationalUnit(int)
     */
    public OrganisationalUnit getOrganisationalUnit(int id) {
        OrganisationalUnit orgUnit = new OrganisationalUnit();
        ResultSet rs = null;

        try {
            getOrganisationalUnit.setInt(1, id);
            rs = getOrganisationalUnit.executeQuery();
            rs.next();

            orgUnit.setId(rs.getInt("id"));
            orgUnit.setName(rs.getString("name"));
            orgUnit.setCredits(rs.getInt("credits"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return orgUnit;
    }

    /**
     * @see TradingPlatform.NetworkProtocol.TradingPlatformDataSource#close()
     */
    public void close() {
        try{
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
