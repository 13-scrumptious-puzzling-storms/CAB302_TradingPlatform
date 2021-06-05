package TradingPlatform;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;


/**
 * Creates a new instance of an organisational unit.
 * Some methods retrieve fields from the database.
 */
public class OrganisationalUnit implements Serializable {
    private static final long serialVersionUID = 541955199052575340L;

    private static NetworkManager networkManager = ClientApp.networkManager;

    String organisationName;
    int organisationCredit;
    int organisationID;

    /**
     * Creates new instance of an organisational unit
     * @param organisationName Name of the organisational unit
     * @param organisationCredit Credit amount held by the organisational unit
     */
    public OrganisationalUnit(String organisationName, int organisationCredit) {
        this.organisationName = organisationName;
        this.organisationCredit = organisationCredit;
    }

    /**
     * Creates new instance of an organisational unit that already exists in the database
     * @param organisationID organisation's unique ID
     */
    public OrganisationalUnit(int organisationID) {
        this.organisationID = organisationID;
        this.organisationName = getName(organisationID);
        this.organisationCredit = getCredits(organisationID);
    }

    /**
     * Creates a null instance of an organisational unit
     */
    public OrganisationalUnit() {
        this.organisationName = "";
        this.organisationCredit = 0;
    }

    /**
     * Sets given ID as the new organisation ID in the given instance of the organisational unit
     * @param newID
     */
    public void setId(int newID){
        this.organisationID = newID;
    }

    /**
     * Returns the organisational Unit ID from the instance of the organisational unit
     * @return the organisational unit ID
     */
    public int getID(){
        return organisationID;
    }

    /**
     * Sets the OrganisationalUnit's name to name for given instance
     *
     * @param name the name of the Organisational Unit
     */
    public void setName(String name) {
        organisationName = name;
    }


    /**
     * Returns the given OrganisationalUnit's name from database
     * @param orgID organisational unit ID
     * @return name of the given Organisational Unit
     */
    public static String getName(int orgID){
        try {
            Request response = networkManager.GetResponse("OrganisationalUnitServer", "getName", new String[]{String.valueOf(orgID)});
            return response.getArguments()[0];
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Returns name of organisational Unit for given instance
     * @return name of organisational unit
     */
    public String getName(){
        return organisationName;
    }


    /**
     * Sets the OrganisationalUnit's credits to credits given for given instance
     *
     * @param credits the credits belonging to the Organisational Unit
     */
    public void setCredits(int credits) {
        this.organisationCredit = credits;
    }

    /**
     * Updates the organisational unit credits in the database to the value of credits
     * @param orgID organisational Unit ID
     * @param credits new value of credits belonging to the organisational unit
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Boolean UpdateOrganisationalUnitCredits(int orgID, int credits)  {
        try {
            Request response = networkManager.GetResponse("OrganisationalUnitServer", "UpdateOrganisationalUnitCredits",
                    new String[]{String.valueOf(orgID), String.valueOf(credits)});
            return Boolean.valueOf(response.getArguments()[0]);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Gets the OrganisationalUnit's organisationCredit from databsase
     */

    /**
     * Gets the Organisational unit's credit from databsase
     * @param orgID organisational unit ID
     * @return number of credits owned by the organisational unit
     */
    public int getCredits(int orgID) {
        try {
            Request response = networkManager.GetResponse("OrganisationalUnitServer", "getCredits", new String[]{String.valueOf(orgID)});
            return Integer.valueOf(response.getArguments()[0]);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets number of credits belonging to the organisational unit for the given instance
     * @return number of credits belonging to the organisational unit
     */
    public int getCredits(){
        return organisationCredit;
    }

//-------------------------------------------------------------------------------------------------------
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
     * @return allAssets
     */
    public ArrayList<OrganisationAsset> getAssets()  {
        try {
            Request response = networkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetsAndQuantity", new String[]{String.valueOf(organisationID)});
            String[][] result = response.getDoubleString();
            ArrayList<OrganisationAsset> assetCollection = new ArrayList<OrganisationAsset>();
            for (String[] i : result) {
                //**Bella can't get any results - coming back to this later
                //System.out.println("method i[0] is: " + i[0]);
                //System.out.println("method i[1] is: " + i[1]);
                //System.out.println("method i[2] is: " + i[2]);
                String asset = "pens";
                int quantity = 0;
                assetCollection.add(new OrganisationAsset(organisationID, asset, quantity));
            }
            //OrganisationAsset(int organisationAssetID, int organisationUnitID, AssetType assetType, int quantity)
            //turn response into ArrayList<>
            //response.getArguments()[0];
            //return response.getArguments()[0];
            return assetCollection;
        }
        catch(Exception e){
            ArrayList<OrganisationAsset> assetCollection = new ArrayList<OrganisationAsset>();
            return assetCollection;

        }
    }

    /**
     * Returns current asset orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return buyAsset
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

