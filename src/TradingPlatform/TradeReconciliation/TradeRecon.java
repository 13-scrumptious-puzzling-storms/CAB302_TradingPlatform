package TradingPlatform.TradeReconciliation;

public class TradeRecon {
    private int sellOrderId;
    private int buyOrderId;
    private int quantity;

    public TradeRecon(int sellOrderId, int buyOrderId, int quantity){
        this.sellOrderId = sellOrderId;
        this.buyOrderId = buyOrderId;
        this.quantity = quantity;
    }

    public int getBuyOrderId() {
        return buyOrderId;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getSellOrderId() {
        return sellOrderId;
    }
}
