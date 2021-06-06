package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.TradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeRecon;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Trade reconcile source is used to add records to the reconcile table, to retrieve reconcilable trade orders
 * and to get historical asset trades (for historical sell price).
 */
public class JDBCTradeReconcileSource implements TradeReconcileSource {

    private static final String INSERT_RECON = "INSERT INTO TradeRecon (sellOrderId, buyOrderId, quantity, createdTime) VALUES (?, ?, ?, strftime('%Y-%m-%d %H:%M:%f','now'))";

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
    private static final String COUNT_RECENT_ASSET_RECONCILE = "" +
            "select \n" +
            "  count(a.name) as num\n" +
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

    private static final String MOST_RECENT_RECONCILE = "select u.name, o.price, r.quantity, r.createdTime, r.buyOrderID, r.sellOrderID from TradeRecon r\n" +
            "    left join (select tradeOrderId, price, t.organisationAssetId from TradeOrders t) as o on (tradeOrderID = r.sellOrderID)\n" +
            "        left join (select p.assetTypeID, p.organisationAssetID from OrganisationAsset p) as q on q.organisationAssetID = o.organisationAssetID\n" +
            "            left join (SELECT name, a.assetTypeID from AssetType a) as u on u.assetTypeID = q.assetTypeID\n" +
            "    order by r.createdTime DESC LIMIT 20;";
    private static final String COUNT_RECENT_RECONCILE = "select count(r.createdTime) as num from TradeRecon r\n" +
            "    left join (select tradeOrderId, price, t.organisationAssetId from TradeOrders t) as o on (tradeOrderID = r.sellOrderID)\n" +
            "        left join (select p.assetTypeID, p.organisationAssetID from OrganisationAsset p) as q on q.organisationAssetID = o.organisationAssetID\n" +
            "            left join (SELECT name, a.assetTypeID from AssetType a) as u on u.assetTypeID = q.assetTypeID\n" +
            "    order by r.createdTime DESC LIMIT 20;";


    private static PreparedStatement insertRecon;
    private static PreparedStatement getSellOrdersAssetType;
    private static PreparedStatement getBuyOrdersAssetType;
    private static PreparedStatement getMatchingBuyOrder;
    private static PreparedStatement getReconcilableAssetTypeIds;
    private static PreparedStatement getMostRecentAssetReconcileInfo;
    private static PreparedStatement countRecentAssetReconcile;
    private static PreparedStatement mostRecentRec;
    private static PreparedStatement countRecentRec;


    /**
     * Creates a reconcile source with the given connection to the database.
     * @param connection the connection to the database
     */
    public JDBCTradeReconcileSource(Connection connection){

        try {
            insertRecon = connection.prepareStatement(INSERT_RECON);
            getSellOrdersAssetType = connection.prepareStatement(GET_CURRENT_SELL_ORDERS_TYPE);
            getBuyOrdersAssetType = connection.prepareStatement(GET_CURRENT_BUY_ORDERS_TYPE);
            getMatchingBuyOrder = connection.prepareStatement(GET_MATCHING_BUY_ORDER);
            getReconcilableAssetTypeIds = connection.prepareStatement(GET_RECONCILABLE_ASSET_TYPE_IDS);
            getMostRecentAssetReconcileInfo = connection.prepareStatement(GET_MOST_RECENT_ASSET_RECONCILE_INFO);
            countRecentAssetReconcile = connection.prepareStatement(COUNT_RECENT_ASSET_RECONCILE);

            mostRecentRec = connection.prepareStatement(MOST_RECENT_RECONCILE);
            countRecentRec = connection.prepareStatement(COUNT_RECENT_RECONCILE);


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

    /**
     * Gets the most recent trade details of each asset type from the database
     * @return A String[][] of the details of the most resent trades
     */
    @Override
    public String[][] getMostRecentAssetTypeTradeDetails() {

        try {
            ResultSet rs = getMostRecentAssetReconcileInfo.executeQuery();
            ResultSet count = countRecentAssetReconcile.executeQuery();
            int num = 0;
            if(count.next()){
                num = count.getInt("num");
            }

            String[][] assetPrices = new String[num][];
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String createdTime = rs.getString("createdTime");
                String[] details = new String[]{ name, price, quantity, createdTime };
                assetPrices[i] = details;
                i++;
            }
            rs.close();
            return assetPrices;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the most recent trade details from the database
     * @return A String[][] of the details of the resent trades
     */
    @Override
    public String[][] getRecentTradeDetails() {

        try {
            ResultSet rs = mostRecentRec.executeQuery();
            ResultSet count = countRecentRec.executeQuery();
            int num = 0;
            if(count.next()){
                num = count.getInt("num");
            }

            String[][] assetPrices = new String[num][];
            int i = 0;
            while (rs.next()) {
                String name = rs.getString("name");
                String price = rs.getString("price");
                String quantity = rs.getString("quantity");
                String createdTime = rs.getString("createdTime");
                String[] details = new String[]{ name, price, quantity, createdTime };
                assetPrices[i] = details;
                i++;
            }
            rs.close();
            return assetPrices;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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
