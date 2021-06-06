package TradingPlatform;

import java.io.IOException;

import static TradingPlatform.ClientApp.networkManager;

/**
 * Creates a new instance of either a BUY or SELL order for a trade
 */
public class Trade{

    private boolean type;
    private AssetType asset;
    private int quantity;
    private int price;
    private int organisation;

    /**
     * Creates a new trade instance
     * @param type The trade type (true for buy, false for sell).
     * @param asset The trade asset.
     * @param quantity The quantity of the asset for the trade.
     * @param price The price of the asset for the trade.
     * @param organisationId The organisation initiating the trade order.
     */
    public Trade(boolean type, AssetType asset, int quantity, int price, int organisationId){
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.price = price;
        this.organisation = organisationId;
    }

    /**
     * Adds a new Trade Order to the database for the values given
     * @param orgAssetId An int showing the organisation asset ID for the trade.
     * @param quantity An int showing the quantity of the asset for the trade.
     * @param type A boolean showing the trade type (true for buy, false for sell).
     * @param price An int showing the price of the asset for the trade.
     */
    public void addTradeOrder(int orgAssetId, int quantity, boolean type, int price) {
        try {
            networkManager.SendRequest("JDBCTradeDataSource", "addTradeOrder", new String[] {String.valueOf(orgAssetId), String.valueOf(quantity), String.valueOf(type), String.valueOf(price)});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the trade to cancelled
     * @param tradeID An int representing the current trade ID
     * @return A Boolean response from the server
     */
    public static Boolean setCancel(int tradeID) {
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
}
