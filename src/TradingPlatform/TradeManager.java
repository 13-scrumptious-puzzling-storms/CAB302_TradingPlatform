package TradingPlatform;

import java.io.IOException;
import java.util.Map;

/**
 * TradeManager manages the listed trades
 */
public class TradeManager {

    private static NetworkManager networkManager = ClientApp.networkManager;

    /**
     *
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

    /**
     * Gets an array of the most recent reconciled trades for each asset
     * @return A String[][] response from the server
     */
    public static String[][] getMostRecentAssetTypeTradeDetails() {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeReconcileSource", "getMostRecentAssetTypeTradeDetails", new String[]{});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }

    /**
     * Gets an array of the most recent reconciled trades
     * @return A String[][] response from the server
     */
    public static String[][] getRecentTradeDetails()  {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeReconcileSource", "getRecentTradeDetails", new String[]{});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }


    /**
     * gets the current SELL orders for an organisation.
     * @param orgID An int showing the organisation ID
     * @return A String[][] response from the server
     */
    public static String[][] getSellOrders(int orgID) {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeDataSource", "getSellOrders", new String[] {String.valueOf(orgID)});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }

    /**
     * gets the current BUY orders for an organisation.
     * @param orgID An int showing the organisation ID
     * @return A String[][] response from the server
     */
    public static String[][] getBuyOrders(int orgID) {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeDataSource", "getBuyOrders", new String[] {String.valueOf(orgID)});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }

    /**
     * sets the trade to cancelled
     * @param tradeID An int representing the current trade ID
     * @return A Boolean response from the server
     */
    public static Boolean setCancel(int tradeID)  {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeDataSource", "setCancel", new String[] {String.valueOf(tradeID)});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getResponse();
    }

    /**
     * Gets the organisation asset ID for a given trade, returns -1 on error
     * @param tradeId An int representing the current trade ID
     * @return An int representing the organisation asset ID
     */
    public static int getOrganisationAssetId(int tradeId) {
        try {
            var response = NetworkManager.GetResponse("JDBCTradeDataSource", "getOrgAssetId", new String[] {String.valueOf(tradeId)});
            var args = response.getArguments();
            return Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets an array of all current Sell orders for an asset
     * @param assetTypeId An int representing the asset type ID
     * @return A String[][] response from the server
     */
    public static String[][] getCurrentSellOrdersPriceAndQuantityForAsset(int assetTypeId){
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeReconcileSource", "getCurrentSellOrdersPriceAndQuantityForAsset", new String[] {String.valueOf(assetTypeId)});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }

    /**
     * Gets an array of all current Buy orders for an asset
     * @param assetTypeId An int representing the asset type ID
     * @return A String[][] response from the server
     */
    public static String[][] getCurrentBuyOrdersPriceAndQuantityForAsset(int assetTypeId)  {
        Request response = null;
        try {
            response = networkManager.GetResponse("JDBCTradeReconcileSource", "getCurrentBuyOrdersPriceAndQuantityForAsset", new String[] {String.valueOf(assetTypeId)});
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response.getDoubleString();
    }
}
