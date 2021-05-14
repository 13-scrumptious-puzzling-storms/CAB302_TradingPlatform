package TradingPlatform.Server;

import TradingPlatform.Asset;
import TradingPlatform.Interfaces.TradeDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE id=?";

    private PreparedStatement getValue;

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


    public float value(String asset, int quantity) {
        return 0;
    }

    /**
     * Sets the type of a trade to either BUY or SELL
     *
     * @param type True for buy, false for sell.
     */
    public void setType(boolean type) {

    }

    /**
     * Gets the type of Trade, either BUY or SELL
     *
     * @return type
     */
    public String GetType() {
        return null;
    }

    /**
     * Sets the asset used for the trade
     *
     * @param asset The asset the trade will be for.
     */
    public void setAsset(Asset asset) {

    }

    /**
     * Gets the asset from the trade
     *
     * @return asset
     */
    public Asset getAsset() {
        return null;
    }

    /**
     * Sets the quantity of assets in the trade
     *
     * @param quantity The quantity of assets in the trade
     */
    public void setQuantity(int quantity) {

    }

    /**
     * Gets the quantity of assets in the trade
     *
     * @return quantity
     */
    public int getQuantity() {
        return 0;
    }

    /**
     * Returns the organisation linked to the current trade
     *
     * @return organisation
     */
    public String getOrganisation() {
        return null;
    }
}
