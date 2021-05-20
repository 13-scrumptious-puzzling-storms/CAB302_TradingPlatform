package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class TradeTests {
    static Connection connection;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
    }

    @Test
    public void testAddTrade(){
        JDBCTradeDataSource trade = new JDBCTradeDataSource(1, connection);
        trade.addTradeOrder(1, 10, 0, 2);
    }

    @Test
    public void testGetValue(){
        JDBCTradeDataSource val = new JDBCTradeDataSource(1, connection);
        assert (val.value(" ", 1) != 0);
    }

    @Test
    public void testGetQuantity(){
        JDBCTradeDataSource quant = new JDBCTradeDataSource(1, connection);
        assert (quant.getQuantity() == 0);
    }

    @Test
    public void testGetAsset(){
        JDBCTradeDataSource asset = new JDBCTradeDataSource(1, connection);
        assert (asset.getAsset() != null);
    }

    @Test
    public void testGetOrg(){
        JDBCTradeDataSource org = new JDBCTradeDataSource(1, connection);
        assert (org.getOrganisation() != null);
    }
}
