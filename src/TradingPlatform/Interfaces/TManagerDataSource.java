package TradingPlatform.Interfaces;

import TradingPlatform.Asset;

import java.util.Map;

public interface TManagerDataSource {
    /**
     * gets the current BUY orders for an asset.
     * @param asset The asset to get the buy orders for.
     * @return buyOrders
     */
     Map<Asset, Integer> getBuyOrders(Asset asset);

    /**
     * gets the current SELL orders for an asset.
     * @param asset The asset to get the sell orders for.
     * @return sellOrders
     */
     Map<Asset, Integer> getSellOrders(Asset asset);

    /**
     * Determines what SELL orders to BUY from if the trade
     * is valid, and calls updateDatabase.
     */
    void executeTrade();

    /**
     * returns true if the trade can be executed
     * @return isValid
     */
    Boolean validTrade();

    /**
     * Talks to the server(?) to update organisation's credits and assets
     * as well as updates the BUY/SELL order to complete if necessary.
     * @param sellOrg The org selling an asset.
     * @param buyOrg The org buying an asset.
     */
    void updateDatabase(int sellOrg, int buyOrg);
}
