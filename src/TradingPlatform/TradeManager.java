package TradingPlatform;

import java.util.Map;

/**
 * TradeManager executes and manages the listed trades
 */
public class TradeManager {

    /**
     * gets the current BUY orders for an asset.
     * @param asset The asset to get the buy orders for.
     * @return buyOrders
     */
    public Map<Asset, Integer> getBuyOrders(Asset asset){
        return null;
    }

    /**
     * gets the current SELL orders for an asset.
     * @param asset The asset to get the sell orders for.
     * @return sellOrders
     */
    public Map<Asset, Integer> getSellOrders(Asset asset){
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
}
