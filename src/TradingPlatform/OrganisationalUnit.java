package TradingPlatform;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrganisationalUnit{
    String organisationName;
    int organisationCredit;
    HashMap<Object, Integer> assetCollection;

    /**
     * Creates new instance of an organisational unit
     * @param organisationName Name of the organisational unit
     * @param organisationCredit Credit amount held by the organisational unit
     */
    public OrganisationalUnit(String organisationName, int organisationCredit) {
    }

    /**
     * Creates new instance of an organisational unit
     * @param organisationID organisation's unique ID
     */
    public OrganisationalUnit(int organisationID){
    }

    /**
     * Adds assets to organisational unit. If asset already exists under organisation name then update quantity.
     * @param organisationName Name of the organisational unit
     * @param asset Asset object type to  added to organisational unit
     * @param quantity Quantity of asset to be added under organisational unit
     */
    public void addAsset(String organisationName, Object asset, int quantity){
    }

    /**
     * Returns entire set of assets owned by the organisational unit
     * @param organisationName Name of the organisational unit
     * @return
     */
    public HashMap getAssets(String organisationName){
        return assetCollection;
    }

    public HashMap getCurrentBuyOrders(String organisationName, Object asset, int quantity){
        return assetCollection;
    }

    public HashMap getCurrentSellOrders(String organisationName, Object asset, int quantity){
        return assetCollection;
    }

}

