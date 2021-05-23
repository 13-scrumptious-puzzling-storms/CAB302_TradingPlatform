package TradingPlatform.JDBCDataSources;

import TradingPlatform.AssetType;
import TradingPlatform.Interfaces.TradeDataSource;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

public class JDBCTradeDataSource implements TradeDataSource {

    private static final String INSERT_TRADE = "INSERT INTO TradeOrders (organisationAssetID, quantity, remainingQuantity, isSellOrder, price, cancelled, createdTime) VALUES (?, ?, ?, ?, ?, ?, datetime('now'))";
    private static final String GET_TRADE = "SELECT * FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_VALUE = "SELECT price FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_TYPE = "SELECT isSellOrder FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_QUANTITY = "SELECT quantity FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_ASSET = "SELECT organisationAssetID FROM TradeOrders WHERE tradeOrderID=?";
    private static final String SET_REMAINING = "UPDATE TradeOrders SET remainingQuantity=? WHERE tradeOrderID=?";
    private static final String GET_REMAINING = "SELECT remainingQuantity FROM TradeOrders WHERE tradeOrderID=?";
    private static final String SET_CANCELLED = "UPDATE TradeOrders SET cancelled='true' WHERE tradeOrderID=?";
    private static final String GET_CANCELLED = "SELECT cancelled FROM TradeOrders WHERE tradeOrderID=?";
    private static final String GET_BUY_ORDERS = "SELECT o.*, organisationUnitId FROM TradeOrders as o\n" +
            "left join organisationAsset as a on a.organisationAssetID = o.organisationAssetID\n" +
            "WHERE assetTypeId=? AND isSellOrder='false'";
    private static final String GET_SELL_ORDERS = "SELECT o.*, organisationUnitId FROM TradeOrders as o\n" +
            "left join organisationAsset as a on a.organisationAssetID = o.organisationAssetID\n" +
            "WHERE assetTypeId=? AND isSellOrder='true'";



    private PreparedStatement addTrade;
    private PreparedStatement getTrade;
    private PreparedStatement getValue;
    private PreparedStatement getType;
    private PreparedStatement getQuantity;
    private PreparedStatement getAsset;
    private PreparedStatement setRemaining;
    private PreparedStatement getRemaining;
    private PreparedStatement setCancelled;
    private PreparedStatement getCancelled;
    private PreparedStatement getBuyOrders;
    private PreparedStatement getSellOrders;


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
            getRemaining = connection.prepareStatement(GET_REMAINING);
            setCancelled = connection.prepareStatement(SET_CANCELLED);
            getCancelled = connection.prepareStatement(GET_CANCELLED);
            getBuyOrders = connection.prepareStatement(GET_BUY_ORDERS);
            getSellOrders = connection.prepareStatement(GET_SELL_ORDERS);

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

    @Override
    public int getRemaining(int tradeId) {
        try {
            getRemaining.clearParameters();
            getRemaining.setInt(1, tradeId);
            ResultSet rs = getRemaining.executeQuery();

            if (rs.next()) {
                int Remaining = rs.getInt("remainingQuantity");
                return Remaining;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    //~~~~~TO FIX METHODS UNDER THIS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public void setCancel(int tradeId){
        try {
            setCancelled.clearParameters();
            setCancelled.setInt(1, tradeId);
            setCancelled.executeUpdate();
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    public Boolean getCancel(int tradeId){
        try{
            getCancelled.clearParameters();
            getCancelled.setInt(1, tradeId);
            ResultSet rs = getCancelled.executeQuery();

            Boolean cancel;

            if (rs.next()) {
                String cancelled = rs.getString("cancelled");

                if(cancelled.equals("false")){
                    cancel = false;
                }else{
                     cancel = true;
                }
                return cancel;
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public HashSet<Integer> getBuyOrders(int orgAssetId){
        try {
            getBuyOrders.clearParameters();
            getBuyOrders.setInt(1, orgAssetId);
            ResultSet rs = getBuyOrders.executeQuery();

            HashSet<Integer> assets = new HashSet<Integer>();
            while (rs.next()) {
                int thing = rs.getInt("tradeOrderID");
                assets.add(thing);
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public HashSet<Integer> getSellOrders(int orgAssetId){
        try {
            getSellOrders.clearParameters();
            getSellOrders.setInt(1, orgAssetId);
            ResultSet rs = getSellOrders.executeQuery();

            HashSet<Integer> assets = new HashSet<Integer>();
            while (rs.next()) {
                int thing = rs.getInt("tradeOrderID");
                assets.add(thing);
            }
            return assets;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
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
