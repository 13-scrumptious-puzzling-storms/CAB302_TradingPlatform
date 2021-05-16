package TradingPlatform.JDBCDataSources;

import TradingPlatform.AccountType;
import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TManagerDataSource;
import TradingPlatform.OrganisationalUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class JDBCTManagerDataSource implements TManagerDataSource {

    private static final String GET_ORDERS = "SELECT * FROM TradeOrders WHERE type=?";

    private PreparedStatement getOrders;

    private Connection connection;

    private int type;
    private String username;
    private AccountType accountType;
    private OrganisationalUnit organisationalUnit;

    public JDBCTManagerDataSource(int type, Connection connection){
        this.connection = connection;
        this.type = type;

        try {
            // Preparing Statements
            getOrders = connection.prepareStatement(GET_ORDERS);

            getOrders.setInt(1, type);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<AssetType, Integer> getBuyOrders(AssetType asset) {
        return null;
    }

    @Override
    public Map<AssetType, Integer> getSellOrders(AssetType asset) {
        return null;
    }

    @Override
    public void executeTrade() {

    }

    @Override
    public Boolean validTrade() {
        return null;
    }

    @Override
    public void updateDatabase(int sellOrg, int buyOrg) {

    }
}
