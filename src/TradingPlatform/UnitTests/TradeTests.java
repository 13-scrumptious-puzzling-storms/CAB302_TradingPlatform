package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;


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
//        trade.addTradeOrder(1, 10, false, 2);
    }

    @Test
    public void testGetValue(){
        assert (trade.value(1) == 5);
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
//        HashSet<Integer> rs = trade.getBuyOrders(3);
//        assert (rs != null);
    }

    @Test
    public void testGetSells(){
//        HashSet<Integer> rs = trade.getSellOrders(3);
//        assert (rs != null);
    }

    @Test
    public void testSetRemaining(){
        assert(trade.getQuantity(4) == 50);
        trade.setRemaining(4, 15);
        assert(trade.getRemaining(4) == 15);
    }

    @Test
    public void testSetCancel(){
        assert (trade.getCancel(1) == false);
        trade.setCancel(1);
        assert (trade.getCancel(1) == true);
    }

    @Test
    public void testGetOrg(){
        Object[][] test = trade.getBuyOrders(2);
        for (Object[] thing : test) {
            for ( Object bit : thing ) {
                System.out.println(bit);
            }
        }
        assert (trade.getBuyOrders(2) != null);
    }
}
