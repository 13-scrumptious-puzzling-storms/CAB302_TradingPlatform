package TradingPlatform;

import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.JDBCDataSources.MockDatabaseFunctions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;


public class TestTrade {

//    static Connection connection;
//    private JDBCTradeDataSource trade;
//
//    @BeforeEach
//    public void init(){
//        MockDatabaseFunctions.InitDb();
//        connection = MockDatabaseFunctions.getConnection();
//        trade = new JDBCTradeDataSource(connection);
//    }
//
//    @Test
//    public void testGetValue(){
//        init();
//        assert (trade.value(1) == 5);
//    }
//
//    @Test
//    public void testGetType(){
//        assert (trade.getType(5).equals("sell"));
//    }
//
//    @Test
//    public void testGetAsset(){
//        assert (trade.getAsset(1) == 1);
//    }
//
//    @Test
//    public void testGetQuantity(){
//        assert (trade.getQuantity(1) == 10);
//    }
//
//    @Test
//    public void testGetBuys(){
////        HashSet<Integer> rs = trade.getBuyOrders(3);
////        assert (rs != null);
//    }
//
//    @Test
//    public void testGetSells(){
////        HashSet<Integer> rs = trade.getSellOrders(3);
////        assert (rs != null);
//    }
//
//    @Test
//    public void testSetRemaining(){
//        assert(trade.getQuantity(4) == 50);
//        trade.setRemaining(4, 15);
//        assert(trade.getRemaining(4) == 15);
//    }
//
//    @Test
//    public void testSetCancel(){
//        assert (!trade.getCancel(1));
//        trade.setCancel(1);
//        assert (trade.getCancel(1));
//    }
//
//    @Test
//    public void testGetOrg(){
//        Object[][] test = trade.getBuyOrders(2);
//        for (Object[] thing : test) {
//            for ( Object bit : thing ) {
//                System.out.println(bit);
//            }
//        }
//        assert (trade.getBuyOrders(2) != null);
//    }
//
//    @AfterAll
//    public static void testAfterAll(){
//        MockDatabaseFunctions.CloseDatabase();
//    }
}
