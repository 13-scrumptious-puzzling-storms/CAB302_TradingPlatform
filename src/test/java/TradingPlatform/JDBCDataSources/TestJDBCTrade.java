package TradingPlatform.JDBCDataSources;

import TradingPlatform.Trade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestJDBCTrade {

    static Connection connection;
    static JDBCTradeDataSource TradeSource;

    @BeforeEach
    public void init(){
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        TradeSource = new JDBCTradeDataSource(connection);
    }

    @Test
    public void testConstructor(){
        new JDBCTradeDataSource(connection);
    }

    @Test
    public void testAddTradeOrder(){
        init();
        TradeSource.addTradeOrder(2, 10, false, 2);
        TradeSource.addTradeOrder(2, 10, true, 2);
    }

    @Test
    public void testCancelled(){
        init();
        int tradeId = 2;
        boolean isCancelled = TradeSource.getCancel(tradeId);
        if (!isCancelled){
            TradeSource.setCancel(tradeId);
        }
        assert(TradeSource.getCancel(tradeId));
    }

    @Test
    public void testRemaining(){
        init();
        int tradeId = 3;
        int remaining = TradeSource.getRemaining(tradeId);
        TradeSource.setRemaining(tradeId, remaining-3);
        assert(TradeSource.getRemaining(tradeId) == remaining-3);
    }
    @Test
    public void testRemaining2(){
        init();
        int tradeId = 10;
        int remaining = TradeSource.getRemaining(tradeId);
        TradeSource.setRemaining(tradeId, remaining-3);
        assert(TradeSource.getRemaining(tradeId) == remaining-3);
    }

    @Test
    public void testType(){
        init();
        assert (TradeSource.getType(5).equals("sell"));
    }

    @Test
    public void testGetBuySellOrders(){
        init();
        assert (TradeSource.getBuyOrders(2) != null);
        assert (TradeSource.getSellOrders(2) != null);
    }

    @Test
    public void testValue(){
        init();
        assert(TradeSource.value(1) == 5);
    }

    @Test
    public void testGetQuantity(){
        init();
        assert(TradeSource.getQuantity(1) == 25);
    }

    @Test
    public void testGetAsset(){
        init();
        assert(TradeSource.getAsset(1) == 5);
    }



    @AfterAll
    public static void DeleteTestDb(){
        MockDatabaseFunctions.CloseDatabase();
    }
}
