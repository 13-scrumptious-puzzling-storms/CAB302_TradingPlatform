package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.TradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeRecon;
import jdk.jfr.Timespan;

import java.sql.*;
import java.util.ArrayList;

public class JDBCTradeReconcileSource implements TradeReconcileSource {

    private static final String INSERT_RECON = "INSERT INTO TradeRecon (sellOrderId, buyOrderId, quantity, createdTime) VALUES (?, ?, ?, datetime('now'))";

    // Need to set isSellOrder
    private static final String GET_CURRENT_BUY_OR_SELL_ORDERS = "SELECT a.assetTypeID, o.* FROM TradeOrders as o " +
            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = ? " +
            "ORDER BY price ASC, createdTime ASC";

    // Need to set isSellOrder and assetTypeId
    private static final String GET_CURRENT_BUY_OR_SELL_ORDERS_TYPE = "SELECT o.* FROM TradeOrders as o " +
            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = ? and a.assetTypeID = ? " +
            "ORDER BY price ASC, createdTime ASC";

    private static final String GET_RECONCILABLE_ASSET_TYPE_IDS = "SELECT DISTINCT a1.assetTypeID " +
            "FROM TradeOrders as sell " +
            "LEFT JOIN OrganisationAsset AS a1 ON a1.organisationAssetID = sell.organisationAssetID " +
            "LEFT JOIN TradeOrders as buy on (" +
                 "buy.remainingQuantity > 0 " +
                 "and buy.cancelled = 'false' " +
                 "and buy.isSellOrder = 'false') " +
            "LEFT JOIN OrganisationAsset AS a2 ON a2.organisationAssetID = buy.organisationAssetID " +
            "WHERE  " +
                 "sell.remainingQuantity > 0 " +
                 "and sell.cancelled = 'false' " +
                 "and sell.isSellOrder = 'true' " +
                 "and a1.assetTypeID = a2.assetTypeID " +
                 "and sell.price <= buy.price";
    
    private static PreparedStatement insertRecon;
    private static PreparedStatement getBuyOrSellOrders;
    private static PreparedStatement getBuyOrSellOrdersAssetType;
    private static PreparedStatement getReconcilableAssetTypeIds;

    private Connection connection;

    public JDBCTradeReconcileSource(Connection connection){
        this.connection = connection;

        try {
            insertRecon = connection.prepareStatement(INSERT_RECON);
            getBuyOrSellOrders = connection.prepareStatement(GET_CURRENT_BUY_OR_SELL_ORDERS);
            getBuyOrSellOrdersAssetType = connection.prepareStatement(GET_CURRENT_BUY_OR_SELL_ORDERS_TYPE);
            getReconcilableAssetTypeIds = connection.prepareStatement(GET_RECONCILABLE_ASSET_TYPE_IDS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void insertTradeRecon(TradeRecon recon) {
        try {
            insertRecon.clearParameters();
            insertRecon.setInt(1, recon.getSellOrderId());
            insertRecon.setInt(2, recon.getBuyOrderId());
            insertRecon.setInt(2, recon.getQuantity());

            insertRecon.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<TradeOrder> getCurrentSellOrders(int assetTypeId) {
        return getCurrentOrders(true, assetTypeId);
    }

    @Override
    public ArrayList<TradeOrder> getCurrentBuyOrders(int assetTypeId) {

        return getCurrentOrders(false, assetTypeId);
    }

    private ArrayList<TradeOrder> getCurrentOrders(boolean isSellOrder, int assetTypeId){
        ArrayList<TradeOrder> tradeOrders = new ArrayList<>();

        try {
            getBuyOrSellOrdersAssetType.clearParameters();
            getBuyOrSellOrders.setBoolean(1, isSellOrder);
            getBuyOrSellOrdersAssetType.setInt(2, assetTypeId);
            ResultSet rs = getBuyOrSellOrdersAssetType.executeQuery();

            if (rs.next()) {
                int tradeOrderId = rs.getInt("tradeOrderID");
                int organisationAssetId = rs.getInt("organisationAssetID");
                int quantity = rs.getInt("quantity");
                int remainingQuantity = rs.getInt("remainingQuantity");
                boolean tradeType = rs.getBoolean("isSellOrder");
                int price = rs.getInt("price");
                boolean cancelled = rs.getBoolean("cancelled");
                Timestamp createdTime = rs.getTimestamp("createdTime");

                TradeOrder order = new TradeOrder(tradeOrderId, organisationAssetId, quantity, remainingQuantity,
                        tradeType, price, cancelled, createdTime);
                tradeOrders.add(order);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tradeOrders;
    }

    @Override
    public ArrayList<Integer> getCurrentReconcilableAssetTypeIds() {
        ArrayList<Integer> assetTypeIds = new ArrayList<>();
        try {
            ResultSet rs = getReconcilableAssetTypeIds.executeQuery();

            if (rs.next()) {
                int assetTypeId = rs.getInt(1);
                assetTypeIds.add(assetTypeId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return assetTypeIds;
    }
}
