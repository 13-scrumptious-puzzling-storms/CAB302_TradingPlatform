package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class TradeTests {
    static Connection connection;
    private JDBCTradeDataSource trade;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
    }

    @BeforeEach
    public void newTrade(){
        trade = new JDBCTradeDataSource(1, connection);
        trade.addTradeOrder(1, 10, false, 2);
    }

    @Test
    public void testGetValue(){
        assert (trade.value(1) == 2);
    }

    @Test
    public void testGetType(){
        assert (trade.GetType(1) == "sell");
    }

    @Test
    public void testGetAsset(){
        assert (trade.getAsset(1) == 1);
    }

    @Test
    public void testGetQuantity(){
        assert (trade.getQuantity(1) == 10);
    }

    @Test
    public void testGetBuys(){
        int[] rs = trade.getBuyOrders(3);
//        int size = rs.length();

        for (int res : rs) {
            System.out.println(res);
        }
        System.out.println(rs);
    }

//    @Test
//    public void testGetOrg(){
//        assert (trade.getOrganisation() != null);
//    }
}
