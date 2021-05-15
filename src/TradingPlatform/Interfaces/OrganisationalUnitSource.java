package TradingPlatform.Interfaces;

import java.util.HashMap;

public interface OrganisationalUnitSource {

    int assignID();

    /**
     *
     * @return organisational units's ID
     */
    int getID();

    /**
     * Sets the OrganisationalUnit's ID to id ****DELETE??***
     *
     * @param id the id of the Organisational Unit
     */
    void setId(int id);

    /**
     * Sets the OrganisationalUnit's name to name
     *
     * @param name the name of the Organisational Unit
     */
    void setName(String name);

    /**
     * Sets the OrganisationalUnit's credits to credits
     *
     * @param credits the credits belonging to the Organisational Unit
     */
    void setCredits(int credits);

    /**
     * Adds assets to organisational unit. If asset already exists under organisation name then update quantity.
     * @param organisationID Organisational unit's unique ID
     * @param asset Asset object type to  added to organisational unit
     * @param quantity Quantity of asset to be added under organisational unit
     */
    void addAsset(int organisationID, Object asset, int quantity);

    /**
     * Returns entire set of assets owned by the organisational unit
     * @param organisationID Organisational Unit's unique ID
     * @return allAssets
     */
    HashMap getAssets(int organisationID);

    /**
     * Returns current asset orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return buyAssets
     */
    HashMap getCurrentBuyOrders(int organisationID);

    /**
     * Returns current asset sell orders placed for organisation
     * @param organisationID Organisational Unit's unique ID
     * @return sellAssets
     */
    HashMap getCurrentSellOrders(int organisationID);

}
