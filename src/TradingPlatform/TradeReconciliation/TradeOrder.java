package TradingPlatform.TradeReconciliation;

import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;

import java.sql.Connection;
import java.sql.Timestamp;

public class TradeOrder {
    private int tradeOrderId;
    private int organisationAssetId;
    private int quantity;
    private int remainingQuantity;
    private boolean isSellOrder;
    private int price;
    private boolean cancelled;
    private Timestamp createdTime;

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

        // Update the remaining quantity in the db
        Connection connection = DBConnection.getInstance();
        var tradeSource = new JDBCTradeDataSource(connection);
        tradeSource.setRemaining(tradeOrderId, remainingQuantity);
    }

    public int getTradeOrderId() {
        return tradeOrderId;
    }

    public int getOrganisationAssetId() {
        return organisationAssetId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isSellOrder() {
        return isSellOrder;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }
}
