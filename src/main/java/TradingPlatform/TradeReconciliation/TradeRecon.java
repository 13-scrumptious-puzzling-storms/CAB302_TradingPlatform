package TradingPlatform.TradeReconciliation;

/**
 * This trade recon class is an object that is just used to store information about a trade reconciliation, it does not
 * interface the server or database in any way.
 */
public class TradeRecon {
    private int sellOrderId;
    private int buyOrderId;
    private int quantity;

    /**
     * Creates a new instance of a readonly trade reconciliation, that doesn't get any of its values from the database
     * This is used in tradeReconcile to store the trade reconciliation details.
     * @param sellOrderId the trade order id for the sell trade order
     * @param buyOrderId the trade order id for the buy trade order
     * @param quantity the quantity of the asset that is being reconciled between the buy and sell orders
     */
    public TradeRecon(int sellOrderId, int buyOrderId, int quantity){
        this.sellOrderId = sellOrderId;
        this.buyOrderId = buyOrderId;
        this.quantity = quantity;
    }

    /**
     * gets the buyOrderId of the trade reconciliation (stored in the object, not retrieved from the database)
     * pre-conditions: assumes that the buy order id exists in the database
     * @return the buy order id of the trade reconciliation
     */
    public int getBuyOrderId() {
        return buyOrderId;
    }

    /**
     * gets the quantity of the trade reconciliation (stored in the object, not retrieved from the database)
     * @return the quantity of the trade reconciliation
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * gets the sellOrderId of the trade reconciliation (stored in the object, not retrieved from the database)
     * pre-conditions: assumes that the sell order id exists in the database
     * @return the sell order id of the trade reconciliation
     */
    public int getSellOrderId() {
        return sellOrderId;
    }
}
