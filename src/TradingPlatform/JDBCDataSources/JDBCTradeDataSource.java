package TradingPlatform.JDBCDataSources;

import TradingPlatform.Asset;
import TradingPlatform.Interfaces.TradeDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_TYPE = "SELECT type FROM TradeOrders Where tradeOrderID=?";

    private PreparedStatement getValue;
    private PreparedStatement getType;

    private Connection connection;

    private int TradeId;

    public JDBCTradeDataSource(int TradeID, Connection connection){
        this.connection = connection;
        this.TradeId = TradeId;

        try {
            Statement st = connection.createStatement();

            getValue = connection.prepareStatement(GET_VALUE);

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
    public void setAsset(Asset asset) {

    }

    @Override
    public Asset getAsset() {
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
