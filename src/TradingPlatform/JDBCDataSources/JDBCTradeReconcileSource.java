package TradingPlatform.JDBCDataSources;

import TradingPlatform.Interfaces.TradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeRecon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    private static PreparedStatement insertRecon;
    private static PreparedStatement getBuyOrSellOrders;
    private static PreparedStatement getBuyOrSellOrdersType;

    private Connection connection;

    public JDBCTradeReconcileSource(Connection connection){
        this.connection = connection;

        try {
            insertRecon = connection.prepareStatement(INSERT_RECON);
            getBuyOrSellOrders = connection.prepareStatement(GET_CURRENT_BUY_OR_SELL_ORDERS);
            getBuyOrSellOrdersType = connection.prepareStatement(GET_CURRENT_BUY_OR_SELL_ORDERS_TYPE);
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
}
