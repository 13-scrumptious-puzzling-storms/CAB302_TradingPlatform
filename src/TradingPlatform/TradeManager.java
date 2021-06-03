package TradingPlatform;

import java.io.IOException;
import java.util.Map;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;

/**
 * TradeManager executes and manages the listed trades
 */
public class TradeManager {
    private static NetworkManager networkManager = ClientApp.networkManager;

    /**
     * gets the current BUY orders for an asset.
     * @param asset The asset to get the buy orders for.
     * @return buyOrders
     */
    public Map<AssetType, Integer> getBuyOrders(AssetType asset){
        return null;
    }

    /**
     * gets the current SELL orders for an asset.
     * @param asset The asset to get the sell orders for.
     * @return sellOrders
     */
    public Map<AssetType, Integer> getSellOrders(AssetType asset){
        return null;
    }

    /**
     * Determines what SELL orders to BUY from if the trade
     * is valid, and calls updateDatabase.
     */
    public void executeTrade(){

    }

    /**
     * returns true if the trade can be executed
     * @return isValid
     */
    public Boolean validTrade(){
        return null;
    }

    /**
     * Talks to the server(?) to update organisation's credits and assets
     * as well as updates the BUY/SELL order to complete if necessary.
     * @param sellOrg The org selling an asset.
     * @param buyOrg The org buying an asset.
     */
    public void updateDatabase(int sellOrg, int buyOrg){

    }

    public static String[][] getMostRecentAssetTypeTradeDetails() throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeReconcileSource", "getMostRecentAssetTypeTradeDetails", new String[]{});
        return response.getDoubleString();
//        return null;
    }


    /**
     * Bella implemented this. not sure where it is meant to go. You may change later
     */
    public static String[][] getSellOrders(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeDataSource", "getSellOrders", new String[] {String.valueOf(orgID)});
        return response.getDoubleString();
    }

    /**
     * Bella implemented this. not sure where it is meant to go. You may change later
     */
    public static String[][] getBuyOrders(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeDataSource", "getBuyOrders", new String[] {String.valueOf(orgID)});
        return response.getDoubleString();
    }
}
