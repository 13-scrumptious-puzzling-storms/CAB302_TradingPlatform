package TradingPlatform;

import java.util.Map;

/**
 * TradeManager executes and manages the listed trades
 */
public class TradeManager {

    /**
     * gets the current BUY orders for an asset
     * @param asset
     * @return buyOrders
     */
    public Map<Object, Integer> getBuyOrders(Object asset){
        return null;
    }

    /**
     * gets the current SELL orders for an asset
     * @param asset
     * @return sellOrders
     */
    public Map<Object, Integer> getSellOrders(Object asset){
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
     * as well as updates the BUY/SELL order to complete if necessary
     */
    public void updateDatabase(int sellOrg, int buyOrg){

    }
}
