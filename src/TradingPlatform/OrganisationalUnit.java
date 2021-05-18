package TradingPlatform;

import java.util.*;

//Testing push 2

/**
 * Creates a new instance of an organisational unit.
 */
public class OrganisationalUnit{
    String organisationName;
    int organisationCredit;
    int organisationID;
    ArrayList<OrganisationAsset> assetCollection;

    /**
     * Creates new instance of an organisational unit
     * @param organisationName Name of the organisational unit
     * @param organisationCredit Credit amount held by the organisational unit
     */
    public OrganisationalUnit(String organisationName, int organisationCredit) {
        this.organisationName = organisationName;
        this.organisationCredit = organisationCredit;
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
        organisationID = id;
    }

    /**
     * Sets the OrganisationalUnit's name to name
     *
     * @param name the name of the Organisational Unit
     */
    public void setName(String name) {
        organisationName = name;
    }

    /**
     * Returns the OrganisationalUnit's name
     *
     * @return  name of the Organisational Unit
     */
    public String getName(int orgID) {
        return organisationName;
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
     * @param asset Asset object type to  added to organisational unit
     * @param quantity Quantity of asset to be added under organisational unit
     * @param addition Boolean variable to hold whether the asset will be added (True) or subtracted (False)
     */
    public void changeAssetBalance(Object asset, int quantity, boolean addition){
//        if (assetCollection.containsKey(asset)){
//            // add or subtract quantity
//            int currentValue = assetCollection.get(asset);
//            if(addition){
//                assetCollection.replace(asset, currentValue, currentValue + quantity);
//            }
//            else { //subtract
//                if(currentValue >= quantity) {
//                    assetCollection.replace(asset, currentValue, currentValue - quantity);
//                }
//            }
//        }
//        else{
//            assetCollection.put(asset, quantity);
//        }
        throw(new UnsupportedOperationException("Not yet implemented"));
    }

    /**
     * Returns entire set of assets owned by the organisational unit
     * @param organisationID Organisational Unit's unique ID
     * @return allAssets
     */
    public ArrayList<OrganisationAsset> getAssets(int organisationID){
        return assetCollection;
    }

    /**
     * Returns current asset orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return buyAssets
     */
    public HashMap getCurrentBuyOrders(int organisationID){
        // return assetCollection;
        throw(new UnsupportedOperationException("Not yet implemented"));
    }

    /**
     * Returns current asset sell orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return sellAssets
     */
    public HashMap getCurrentSellOrders(int organisationID){
        throw(new UnsupportedOperationException("Not yet implemented"));
//        return assetCollection;
    }

}

