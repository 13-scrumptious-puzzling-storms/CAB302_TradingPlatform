package TradingPlatform.TradeReconciliation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

public class TestTradeOrder {

    static TradeOrder testSellOrder = new TradeOrder(2, 3, 10, 5,
            true, 20, false, Timestamp.valueOf("2000-01-01 01:12:12.123456789"));;
    static TradeOrder testBuyOrder = new TradeOrder(1, 2, 50, 49,
            false, 2, true, Timestamp.valueOf("2020-01-01 01:12:12.123456789"));;

    @Test
    public void testGetTradeOrderId(){
        assert(testSellOrder.getTradeOrderId() == 2);
    }

    @Test
    public void testGetTradeOrganisationAssetId(){
        assert(testSellOrder.getOrganisationAssetId() == 3);
    }

    @Test
    public void testGetTradeQuantity(){
        assert(testSellOrder.getQuantity() == 10);
    }

    @Test
    public void testGetTradeRemainingQuantity(){
        assert(testSellOrder.getRemainingQuantity() == 5);
    }

    @Test
    public void testGetPrice(){
        assert(testSellOrder.getPrice() == 20);
    }

    @Test
    public void testIsNotCancelled(){
        assert(!testSellOrder.isCancelled());
    }

    @Test
    public void testIsCancelled(){
        assert(testBuyOrder.isCancelled());
    }

    @Test
    public void testGetIsSellOrder(){
        assert(testSellOrder.isSellOrder());
    }

    @Test
    public void testGetIsBuyOrder(){
        assert(!testBuyOrder.isSellOrder());
    }

    @Test
    public void testGetCreatedTime(){
        assert(testSellOrder.getCreatedTime().equals(Timestamp.valueOf("2000-01-01 01:12:12.123456789")));
    }

    @Test
    public void testReduceRemainingQuantity(){
        testBuyOrder.reduceRemainingQuantity(10);
        assert (testBuyOrder.getRemainingQuantity() == 39);
    }
}
