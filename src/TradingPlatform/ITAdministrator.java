package TradingPlatform;

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
     * @param password User's password
     * @param unit Organisational unit that the user belongs to
     */
    public void CreateNewUser(String username, String password, OrganisationalUnit unit){
    }

    /**
     * Creates a new IT administrator with a username and password, and assigns them to the IT Administrators
     * Organisational Unit.
     *
     * @param username Admin's username
     * @param password Admin's password
     */
    public void CreateNewITAdmin(String username, String password){

    }

    /**
     * Edits the number of credits an organisational unit has.
     *
     * @param unit The unit that will be edited.
     * @param credits The new amount of credits the unit will have.
     */
    public void EditOrganisationalUnits(OrganisationalUnit unit, int credits){
    }

    /**
     * Edits the number of an asset that an organisational unit has.
     *
     * @param unit The unit that will be edited.
     * @param asset The unit asset to be edited.
     * @param numAsset The new number of the asset that the organisation unit will have.
     */
    public void EditOrganisationalAsset(OrganisationalUnit unit, Asset asset, int numAsset){
    }

    /**
     * Creates a new asset type, and adds it to the database.
     *
     * @param assetName The name of the asset.
     */
    public void CreateNewAssetType(String assetName) {
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.ADMINISTRATOR;
    }
}
