package TradingPlatform.TradeReconciliation;

import java.sql.Timestamp;

/**
 * This tradeorder class is an object that is just used to store information about a trade order, it does not
 * interface the server or database in any way.
 */
public class TradeOrder {
    private int tradeOrderId;
    private int organisationAssetId;
    private int quantity;
    private int remainingQuantity;
    private boolean isSellOrder;
    private int price;
    private boolean cancelled;
    private Timestamp createdTime;

    /**
     * Creates a new instance of a tradeorder, that doesn't get any of its values from the database
     * This is used in tradeReconcile to store the trade order details.
     * @param tradeOrderId the trade order id
     * @param organisationAssetId the id of the organisation asset of the trade
     * @param quantity the total trade quantity
     * @param remainingQuantity the remaining quantity of the trade
     * @param isSellOrder whether the trade is a buy or sell order
     * @param price the price per unit for the trade
     * @param cancelled whether the trade is cancelled
     * @param createdTime the UTC created time of the trade
     */
    public TradeOrder(int tradeOrderId, int organisationAssetId, int quantity, int remainingQuantity,
                      boolean isSellOrder, int price, boolean cancelled, Timestamp createdTime){
        this.tradeOrderId = tradeOrderId;
        this.organisationAssetId = organisationAssetId;
        this.quantity = quantity;
        this.remainingQuantity = remainingQuantity;
        this.isSellOrder = isSellOrder;
        this.price = price;
        this.cancelled = cancelled;
        this.createdTime = createdTime;
    }

    /**
     * Reduces the trade's remaining quantity
     * @param reduceQuantity the quantity to reduce the remaining quantity by
     */
    public void reduceRemainingQuantity(int reduceQuantity){
        remainingQuantity = remainingQuantity - reduceQuantity;
    }

    /**
     * gets the tradeOrderId of the trade (stored in the object, not retrieved from the database)
     * @return the trade order id of the trade
     */
    public int getTradeOrderId() {
        return tradeOrderId;
    }

    /**
     * gets the organisation asset id of the trade (stored in the object, not retrieved from the database)
     * @return the organisation asset id of the trade
     */
    public int getOrganisationAssetId() {
        return organisationAssetId;
    }

    /**
     * gets the total quantity of the trade (stored in the object, not retrieved from the database)
     * @return the total quantity of the trade
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * gets the remaining quantity of the trade (stored in the object, not retrieved from the database)
     * @return the remaining quantity of the trade
     */
    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    /**
     * gets the price per unit of the trade (stored in the object, not retrieved from the database)
     * @return the price per unit of the trade
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets whether the trade has been cancelled (stored in the object, not retrieved from the database)
     * @return whether the trade has been cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * gets if the trade is a sell or buy order (stored in the object, not retrieved from the database)
     * @return true if the trade is a sell order, false if it is a buy order
     */
    public boolean isSellOrder() {
        return isSellOrder;
    }

    /**
     * gets the UTC created time of the trade (stored in the object, not retrieved from the database)
     * @return the UTC created time of the trade
     */
    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
