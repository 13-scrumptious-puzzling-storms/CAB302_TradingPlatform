package TradingPlatform;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Testing push

/**
 * Creates a new instance of an organisational unit.
 */
public class OrganisationalUnit{
    String organisationName;
    int organisationCredit;
    int organisationID;
    static int nextID;
    HashMap<Object, Integer> assetCollection;

    /**
     * Creates new instance of an organisational unit
     * @param organisationName Name of the organisational unit
     * @param organisationCredit Credit amount held by the organisational unit
     */
    public OrganisationalUnit(String organisationName, int organisationCredit) {
        this.organisationName = organisationName;
        this.organisationCredit = organisationCredit;
        this.organisationID = assignID();
    }

//    /**
//     * Creates new instance of an organisational unit
//     * @param organisationID organisation's unique ID
//     */
//    public OrganisationalUnit(int organisationID){
//    }

    /**
     * Creates a null instance of an organisational unit
     */
    public OrganisationalUnit() {
        this.organisationName = "";
        this.organisationCredit = 0;
        this.organisationID = assignID();
    }

    private int assignID() {
        nextID = nextID + 1;
        return nextID;
    }

    public int getID(){
        return organisationID;
    }

    /**
     * Sets the OrganisationalUnit's ID to id ****DELETE??***
     *
     * @param id the id of the Organisational Unit
     */
    public void setId(int id) {
        this.organisationID = id;
    }

    /**
     * Sets the OrganisationalUnit's name to name
     *
     * @param name the name of the Organisational Unit
     */
    public void setName(String name) {
        this.organisationName = name;
    }

    /**
     * Sets the OrganisationalUnit's credits to credits
     *
     * @param credits the credits belonging to the Organisational Unit
     */
    public void setCredits(int credits) {
        this.organisationCredit = credits;
    }

    /**
     * Adds assets to organisational unit. If asset already exists under organisation name then update quantity.
     * @param organisationID Organisational unit's unique ID
     * @param asset Asset object type to  added to organisational unit
     * @param quantity Quantity of asset to be added under organisational unit
     */
    public void addAsset(int organisationID, Object asset, int quantity){
    }

    /**
     * Returns entire set of assets owned by the organisational unit
     * @param organisationID Organisational Unit's unique ID
     * @return allAssets
     */
    public HashMap getAssets(int organisationID){
        return assetCollection;
    }

    /**
     * Returns current asset orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return buyAssets
     */
    public HashMap getCurrentBuyOrders(int organisationID){
        return assetCollection;
    }

    /**
     * Returns current asset sell orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return sellAssets
     */
    public HashMap getCurrentSellOrders(int organisationID){
        return assetCollection;
    }

}

