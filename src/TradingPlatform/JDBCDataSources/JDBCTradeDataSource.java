package TradingPlatform.JDBCDataSources;

import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TradeDataSource;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String INSERT_TRADE = "INSERT INTO TradeOrders (organisationAssetID, quantity, remainingQuantity, type, price, cancelled, createdTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_TRADE = "SELECT * FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_TYPE = "SELECT type FROM TradeOrders Where tradeOrderID=?";
    private static final String GET_QUANTITY = "SELECT quantity FROM TradeOrders Where tradeOrderID=?";
    private static final String GET_ASSET = "SELECT organisationAssetID FROM TradeOrders Where tradeOrderID=?";
    private static final String GET_PRICE = "SELECT price FROM TradeOrders WHERE =?";

    private PreparedStatement addTrade;
    private PreparedStatement getTrade;
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

            addTrade = connection.prepareStatement(INSERT_TRADE);
            getTrade = connection.prepareStatement(GET_TRADE);
            getValue = connection.prepareStatement(GET_VALUE);
            getType = connection.prepareStatement(GET_TYPE);
            getQuantity = connection.prepareStatement(GET_QUANTITY);
            getAsset = connection.prepareStatement(GET_ASSET);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addTradeOrder(int orgAssetId, int quantity, int type, int price){
        try {
            addTrade.clearParameters();
            addTrade.setInt(1, orgAssetId);
            addTrade.setInt(2, quantity);
            addTrade.setInt(3, quantity);
            addTrade.setInt(4, type);
            addTrade.setInt(5, price);
            addTrade.setBoolean(6, false);
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-DD hh:mm:ss[.fffffffff]");
            LocalDateTime now = LocalDateTime.now();

            addTrade.setTimestamp(7, Timestamp.valueOf(now));
            addTrade.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
