package TradingPlatform.JDBCDataSources;

import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TradeDataSource;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String INSERT_TRADE = "INSERT INTO TradeOrders (organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (?, ?, ?, ?, ?, ?, datetime('now'))";
    private static final String GET_TRADE = "SELECT * FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_TYPE = "SELECT isSellOrder FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_QUANTITY = "SELECT quantity FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_ASSET = "SELECT organisationAssetID FROM TradeOrders WHERE tradeOrderID=?";
    private static final String SET_REMAINING = "UPDATE TradeOrders SET remainingQuantity=? WHERE tradeOrderID=?";
    private static final String SET_CANCELLED = "UPDATE TradeOrders SET cancelled=? WHERE tradeOrderID=?";
    private static final String GET_BUY_ORDERS = "SELECT o.*, organisationUnitId FROM TradeOrders as o\n" +
            "left join organisationAsset as a on a.organisationAssetID = o.organisationAssetID\n" +
            "WHERE assetTypeId=? AND isSellOrder='false'";



    private PreparedStatement addTrade;
    private PreparedStatement getTrade;
    private PreparedStatement getValue;
    private PreparedStatement getType;
    private PreparedStatement getQuantity;
    private PreparedStatement getAsset;
    private PreparedStatement setRemaining;
    private PreparedStatement setCancelled;
    private PreparedStatement getBuyOrders;


    private Connection connection;

    private int TradeId;

    public JDBCTradeDataSource(int TradeID, Connection connection){
        this.connection = connection;
        this.TradeId = TradeID;

        try {
            Statement st = connection.createStatement();

            addTrade = connection.prepareStatement(INSERT_TRADE);
            getTrade = connection.prepareStatement(GET_TRADE);
            getValue = connection.prepareStatement(GET_VALUE);
            getType = connection.prepareStatement(GET_TYPE);
            getQuantity = connection.prepareStatement(GET_QUANTITY);
            getAsset = connection.prepareStatement(GET_ASSET);
            setRemaining = connection.prepareStatement(SET_REMAINING);
            setCancelled = connection.prepareStatement(SET_CANCELLED);
            getBuyOrders = connection.prepareStatement(GET_BUY_ORDERS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addTradeOrder(int orgAssetId, int quantity, boolean type, int price){
        try {
            addTrade.clearParameters();
            addTrade.setInt(1, orgAssetId);
            addTrade.setInt(2, quantity);
            addTrade.setInt(3, quantity);
            addTrade.setBoolean(4, type);
            addTrade.setInt(5, price);
            addTrade.setBoolean(6, false);
            addTrade.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int value(int tradeId) {
        try {
            getValue.clearParameters();
            getValue.setInt(1, tradeId);
            ResultSet rs = getValue.executeQuery();

            if (rs.next()) {
                int price = rs.getInt("price");
                return price;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public String GetType(int tradeId) {
        try {
            getType.clearParameters();
            getType.setInt(1, tradeId);
            ResultSet rs = getType.executeQuery();

            if (rs.next()) {
                Boolean tempType = rs.getBoolean("isSellOrder");
                String type;
                if(tempType == true){
                    type = "sell";
                }else{
                    type = "buy";
                }
                return type;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int getAsset(int tradeId) {
        try {
            getAsset.clearParameters();
            getAsset.setInt(1, tradeId);
            ResultSet rs = getAsset.executeQuery();

            if (rs.next()) {
                int orgAssetId = rs.getInt("organisationAssetId");
                return orgAssetId;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getQuantity(int tradeId) {
        try {
            getQuantity.clearParameters();
            getQuantity.setInt(1, tradeId);
            ResultSet rs = getQuantity.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                return quantity;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public void setRemaining(int tradeId, int amount){
        try {
            setRemaining.clearParameters();
            setRemaining.setInt(1, amount);
            setRemaining.setInt(2, tradeId);
            setRemaining.executeUpdate();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //~~~~~TO FIX METHODS UNDER THIS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void setCancel(int tradeId){
        try {
            setCancelled.clearParameters();
            setCancelled.setBoolean(1, true);
            setCancelled.setInt(2, tradeId);
            setRemaining.executeUpdate();
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public int[] getBuyOrders(int orgAssetId){
        try {
            getBuyOrders.clearParameters();
            getBuyOrders.setInt(1, orgAssetId);
            ResultSet rs = getBuyOrders.executeQuery();

            //int size = rs.getFetchSize();
            int i = 0;
            int size =0;
            int[] assets = new int[size];
            if (rs != null)
            {
                rs.last();    // moves cursor to the last row
                size = rs.getRow(); // get row id
            }
            while (rs.next()) {

                int thing = rs.getInt("tradeOrderID");
                System.out.println(thing);
                assets[i] = thing;
                i++;
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public AssetType[] getSellOrders(int orgAssetId){
        return null;
    }
//    @Override
//    public String getOrganisation(int tradeId) {
//        try {
//            getOrg.clearParameters();
//            getOrg.setInt(1, tradeId);
//            ResultSet rs = getOrg.executeQuery();
//
//            if (rs.next()) {
//                int orgId = rs.getInt("");
//                return orgId;
//            }
//        }
//        catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }
}
