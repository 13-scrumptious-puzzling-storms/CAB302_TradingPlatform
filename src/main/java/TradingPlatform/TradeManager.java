package TradingPlatform;

import java.io.IOException;
import java.util.Map;

/**
 * TradeManager manages the listed trades
 */
public class TradeManager {

    private static NetworkManager networkManager = ClientApp.networkManager;

    /**
     * Gets an array of the most recent reconciled trades for each asset
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getMostRecentAssetTypeTradeDetails() throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeReconcileSource", "getMostRecentAssetTypeTradeDetails", new String[]{});
        return response.getDoubleString();
    }

    /**
     * Gets an array of the most recent reconciled trades
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getRecentTradeDetails() throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeReconcileSource", "getRecentTradeDetails", new String[]{});
        return response.getDoubleString();
    }


    /**
     * gets the current SELL orders for an organisation.
     * @param orgID An int showing the organisation ID
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getSellOrders(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeDataSource", "getSellOrders", new String[] {String.valueOf(orgID)});
        return response.getDoubleString();
    }

    /**
     * gets the current BUY orders for an organisation.
     * @param orgID An int showing the organisation ID
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getBuyOrders(int orgID) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeDataSource", "getBuyOrders", new String[] {String.valueOf(orgID)});
        return response.getDoubleString();
    }

    /**
     * Gets an array of all current Sell orders for an asset
     * @param assetTypeId An int representing the asset type ID
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getCurrentSellOrdersPriceAndQuantityForAsset(int assetTypeId) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeReconcileSource", "getCurrentSellOrdersPriceAndQuantityForAsset", new String[] {String.valueOf(assetTypeId)});
        return response.getDoubleString();
    }

    /**
     * Gets an array of all current Buy orders for an asset
     * @param assetTypeId An int representing the asset type ID
     * @return A String[][] response from the server
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String[][] getCurrentBuyOrdersPriceAndQuantityForAsset(int assetTypeId) throws IOException, ClassNotFoundException {
        Request response = networkManager.GetResponse("JDBCTradeReconcileSource", "getCurrentBuyOrdersPriceAndQuantityForAsset", new String[] {String.valueOf(assetTypeId)});
        return response.getDoubleString();
    }
}
