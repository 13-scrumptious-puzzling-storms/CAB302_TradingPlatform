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
    public void testGetValue(){
        JDBCTradeDataSource val = new JDBCTradeDataSource(1, connection);
        assert (val.value(" ", 1) != 0);
    }
}
