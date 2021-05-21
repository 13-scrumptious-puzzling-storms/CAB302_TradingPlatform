package TradingPlatform.JDBCDataSources;

import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TradeDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_TYPE = "SELECT type FROM TradeOrders Where tradeOrderID=?";
    private static final String GET_QUANTITY = "SELECT quantity FROM TradeOrders Where tradeOrderID=?";
    private static final String GET_ASSET = "SELECT organisationAssetID FROM TradeOrders Where tradeOrderID=?";

    private PreparedStatement getValue;
    private PreparedStatement getType;
    private PreparedStatement getQuantity;
    private PreparedStatement getAsset;

    private Connection connection;

    private int TradeId;

    public JDBCTradeDataSource(int TradeID, Connection connection){
        this.connection = connection;
        this.TradeId = TradeId;

        try {
            Statement st = connection.createStatement();

            getValue = connection.prepareStatement(GET_VALUE);
            getType = connection.prepareStatement(GET_TYPE);
            getQuantity = connection.prepareStatement(GET_QUANTITY);
            getAsset = connection.prepareStatement(GET_ASSET);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public float value(String asset, int quantity) {
        return 0;
    }

    @Override
    public void setType(boolean type) {

    }

    @Override
    public String GetType() {
        return null;
    }

    @Override
    public void setAsset(AssetType asset) {

    }

    @Override
    public AssetType getAsset() {
        return null;
    }

    @Override
    public void setQuantity(int quantity) {

    }

    @Override
    public int getQuantity() {
        return 0;
    }

    @Override
    public String getOrganisation() {
        return null;
    }
}
