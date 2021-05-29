package TradingPlatform;

import java.io.IOException;

/**
 * An IT Administrator is a user that has extra permissions to manage Organisational units and users.
 */
public class ITAdministrator extends User {
    /**
     * @param userID User's userid.
     */
    public ITAdministrator(int userID) {
        super(userID);
    }

    /**
     * Creates a new user and assigns them passwords and an organisational unit.
     *
     * @param username User's username
     * @param hashedPassword User's hashed password
     * @param unit Organisational unit that the user belongs to
     */
    public void CreateNewMember(String username, String hashedPassword, OrganisationalUnit unit){
        CreateUser(username, hashedPassword, unit, AccountType.MEMBER);
    }

    /**
     * Creates a new IT administrator with a username and password, and assigns them to the IT Administrators
     * Organisational Unit (the unit of this IT admin).
     *
     * @param username Admin's username
     * @param hashedPassword Admin's hashed password
     */
    public void CreateNewITAdmin(String username, String hashedPassword){
        CreateUser(username, hashedPassword, this.getOrganisationalUnit(), AccountType.ADMINISTRATOR);
    }

    /**
     * Create a new user and add them to the server.
     *
     * @param username User's username
     * @param hashedPassword User's password
     * @param unit Organisational unit that the user belongs to
     * @param accountType The user's account type (member or admin)
     */
    private void CreateUser(String username, String hashedPassword, OrganisationalUnit unit, AccountType accountType){
        try {
            NetworkManager.SendRequest("JDBCUserDataSource", "addUser",
                    new String[] {username, hashedPassword, accountType.name(), Integer.toString(unit.getID())});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the number of credits an organisational unit has.
     *
     * @param unit The unit that will be edited.
     * @param credits The new amount of credits the unit will have.
     */
    public void EditOrganisationalUnits(OrganisationalUnit unit, int credits){
        // Update the client program OrganisationalUnit instance's credits
        unit.setCredits(credits);

        // Update the credits in the server
        try {
            NetworkManager.SendRequest("OrganisationalUnitServer", "setCredits",
                    new String[] {Integer.toString(unit.getID()), Integer.toString(credits)});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edits the number of an asset that an organisational unit has.
     *
     * @param oAsset The organisation asset that will be edited.
     * @param numAsset The new number of the asset that the organisation unit will have.
     */
    public void EditOrganisationalAsset(OrganisationAsset oAsset, int numAsset){
        // NOT YET IMPLEMENTED -- OrganisationAsset doesn't have a setQuantity method
    }

    /**
     * Creates a new asset type, and adds it to the database.
     *
     * @param assetName The name of the asset.
     */
    public void CreateNewAssetType(String assetName) {
        try {
            NetworkManager.SendRequest("JDBCOrganisationalAsset", "addAssetType", new String[] {assetName});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.ADMINISTRATOR;
    }
}
