package TradingPlatform.TradeReconciliation;

import org.junit.jupiter.api.Test;

public class TestTradeRecon {

    static TradeRecon reconciliation = new TradeRecon(20, 24, 15);


    @Test
    public void testGetBuyOrderId(){
        assert(reconciliation.getBuyOrderId() == 24);
    }

    @Test
    public void testGetSellOrderId(){
        assert(reconciliation.getSellOrderId() == 20);
    }

    @Test
    public void testGetQuantity(){
        assert(reconciliation.getQuantity() == 15);
    }

}
