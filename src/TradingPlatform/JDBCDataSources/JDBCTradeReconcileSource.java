package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.TradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeRecon;

import java.sql.*;
import java.util.ArrayList;

public class JDBCTradeReconcileSource implements TradeReconcileSource {

    private static final String INSERT_RECON = "INSERT INTO TradeRecon (sellOrderId, buyOrderId, quantity, createdTime) VALUES (?, ?, ?, strftime('%Y-%m-%d %H:%M:%f','now'))";

    // Need to set isSellOrder
//    private static final String GET_CURRENT_BUY_OR_SELL_ORDERS = "SELECT a.assetTypeID, o.* FROM TradeOrders as o " +
//            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
//            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = ? " +
//            "ORDER BY price ASC, createdTime ASC";

    // Need to set assetTypeId
    private static final String GET_CURRENT_SELL_ORDERS_TYPE = "SELECT o.* FROM TradeOrders as o " +
            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = 'true' and a.assetTypeID = ? " +
            "ORDER BY price ASC, createdTime ASC";

    // Need to set assetTypeId
    private static final String GET_CURRENT_BUY_ORDERS_TYPE = "SELECT o.* FROM TradeOrders as o " +
            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = 'false' and a.assetTypeID = ? " +
            "ORDER BY createdTime ASC";

    // Need to set assetTypeId and minPrice
    private static final String GET_MATCHING_BUY_ORDER = "SELECT o.* FROM TradeOrders as o " +
            "left join OrganisationAsset as a on a.organisationAssetID = o.organisationAssetID " +
            "WHERE remainingQuantity > 0 and cancelled = 'false' and isSellOrder = 'false' and a.assetTypeID = ? and o.price >= ? " +
            "ORDER BY createdTime ASC";

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
//    private static PreparedStatement getBuyOrSellOrders;
    private static PreparedStatement getSellOrdersAssetType;
    private static PreparedStatement getBuyOrdersAssetType;
    private static PreparedStatement getMatchingBuyOrder;
    private static PreparedStatement getReconcilableAssetTypeIds;

    private Connection connection;

    public JDBCTradeReconcileSource(Connection connection){
        this.connection = connection;

        try {
            insertRecon = connection.prepareStatement(INSERT_RECON);
//            getBuyOrSellOrders = connection.prepareStatement(GET_CURRENT_BUY_OR_SELL_ORDERS);
            getSellOrdersAssetType = connection.prepareStatement(GET_CURRENT_SELL_ORDERS_TYPE);
            getBuyOrdersAssetType = connection.prepareStatement(GET_CURRENT_BUY_ORDERS_TYPE);
            getMatchingBuyOrder = connection.prepareStatement(GET_MATCHING_BUY_ORDER);
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
            insertRecon.setInt(3, recon.getQuantity());

            insertRecon.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return assetTypeIds;
    }

    @Override
    public TradeOrder getMatchingBuyOrder(int assetTypeId, int minPrice) {
        TradeOrder buyOrder = null;
        try {
            getMatchingBuyOrder.clearParameters();
            getMatchingBuyOrder.setInt(1, assetTypeId);
            getMatchingBuyOrder.setInt(2, minPrice);
            ResultSet rs = getMatchingBuyOrder.executeQuery();

            if (rs.next()) {
                buyOrder = getTradeOrderFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return buyOrder;
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
        PreparedStatement getOrders;
        if (isSellOrder)
            getOrders = getSellOrdersAssetType;
        else
            getOrders = getBuyOrdersAssetType;

        try {
            getOrders.clearParameters();
            getOrders.setInt(1, assetTypeId);
            ResultSet rs = getOrders.executeQuery();

            if (rs.next()) {
                TradeOrder order = getTradeOrderFromResultSet(rs);
                tradeOrders.add(order);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tradeOrders;
    }

    private TradeOrder getTradeOrderFromResultSet(ResultSet rs) throws SQLException {
        int tradeOrderId = rs.getInt("tradeOrderID");
        int organisationAssetId = rs.getInt("organisationAssetID");
        int quantity = rs.getInt("quantity");
        int remainingQuantity = rs.getInt("remainingQuantity");
        boolean tradeType = rs.getBoolean("isSellOrder");
        int price = rs.getInt("price");
        boolean cancelled = rs.getBoolean("cancelled");
        Timestamp createdTime = rs.getTimestamp("createdTime");

        return new TradeOrder(tradeOrderId, organisationAssetId, quantity, remainingQuantity,
                tradeType, price, cancelled, createdTime);
    }
}
