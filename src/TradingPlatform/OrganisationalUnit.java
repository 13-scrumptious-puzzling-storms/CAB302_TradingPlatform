package TradingPlatform;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

//Testing push 2

/**
 * Creates a new instance of an organisational unit.
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


    public void setId(int newID){
        this.organisationID = newID;
    }

    public int getID(){
        return organisationID;
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
     * Returns the given OrganisationalUnit's name
     *
     * @return  name of the given Organisational Unit
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

    public String getName(){
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
     * Gets the OrganisationalUnit's organisationCredit
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

    public int getCredits(){
        return organisationCredit;
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
     * @return allAssets
     */
    public ArrayList<OrganisationAsset> getAssets() throws IOException, ClassNotFoundException {
        try {
            Request response = networkManager.GetResponse("JDBCOrganisationalAsset", "getOrganisationAssetsQuantity", new String[]{String.valueOf(organisationID)});
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

