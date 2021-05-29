package TradingPlatform.JDBCDataSources;

import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeRecon;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private static final String GET_MOST_RECENT_ASSET_RECONCILE_INFO = "" +
            "select \n" +
            "  a.name\n" +
            "  , r1.price\n" +
            "  , r1.quantity\n" +
            "  , r1.createdTime\n" +
            "from AssetType a \n" +
            "left join (\n" +
            "    select \n" +
            "        oa.assetTypeID\n" +
            "        , s.price\n" +
            "        , r.tradeReconId\n" +
            "        , r.quantity\n" +
            "        , r.createdTime \n" +
            "    from TradeRecon r\n" +
            "    left join TradeOrders s on s.tradeOrderID = r.sellOrderId\n" +
            "    left join OrganisationAsset oa on oa.organisationAssetID = s.organisationAssetID\n" +
            ") as r1 on (r1.assetTypeID = a.assetTypeID)\n" +
            "left join (\n" +
            "    select \n" +
            "        oa.assetTypeID\n" +
            "        , s.price\n" +
            "        , r.tradeReconId\n" +
            "        , r.quantity\n" +
            "        , r.createdTime \n" +
            "    from TradeRecon r\n" +
            "    left join TradeOrders s on s.tradeOrderID = r.sellOrderId\n" +
            "    left join OrganisationAsset oa on oa.organisationAssetID = s.organisationAssetID\n" +
            ") as r2 on (r2.assetTypeID = a.assetTypeID AND \n" +
            "    (r1.createdTime < r2.createdTime OR (r1.createdTime = r2.createdTime AND r1.tradeReconId < r2.tradeReconId)))\n" +
            "where r2.tradeReconId is null and r1.tradeReconId is not null;";

    private static PreparedStatement insertRecon;
//    private static PreparedStatement getBuyOrSellOrders;
    private static PreparedStatement getSellOrdersAssetType;
    private static PreparedStatement getBuyOrdersAssetType;
    private static PreparedStatement getMatchingBuyOrder;
    private static PreparedStatement getReconcilableAssetTypeIds;
    private static PreparedStatement getMostRecentAssetReconcileInfo;

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
            getMostRecentAssetReconcileInfo = connection.prepareStatement(GET_MOST_RECENT_ASSET_RECONCILE_INFO);

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

            while (rs.next()) {
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
    public Map<AssetType, String[]> getMostRecentAssetTypeTradeDetails() {
        HashMap<AssetType, String[]> assetPrices = new HashMap<>();
        try {
            ResultSet rs = getMostRecentAssetReconcileInfo.executeQuery();
            while (rs.next()) {
                AssetType type = new AssetType(rs.getString("name"));
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String createdTime = rs.getString("createdTime");
                String[] details = new String[]{ price, quantity, createdTime };
                assetPrices.put(type, details);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return assetPrices;
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

            while (rs.next()) {
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
