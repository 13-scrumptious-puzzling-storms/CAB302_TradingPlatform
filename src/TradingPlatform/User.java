package TradingPlatform;

import TradingPlatform.Interfaces.UserDataSource;

import java.io.IOException;

/**
 * A user is a part of an organisational unit, and has their own username, password,
 * and account type.
 */
public class User implements UserDataSource {
    private final OrganisationalUnit unit;
    private final String username;
    private final AccountType accountType;
    private final int userID;

    /**
     * Instantiates a user from their userid, getting their information from the database
     * @param userID The user's userid
     */
    public User(int userID) throws IOException, ClassNotFoundException {
        this.userID = userID;

        // Get the user's data from the server
        Request response = null;
        try {
            response = NetworkManager.GetResponse("JDBCUserDataSource", "getUser", new String[] {String.valueOf(userID)});
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Check for not null response
        if (response != null && response.getArguments() != null) {
            String[] details = response.getArguments();
            this.username = details[0];
            this.accountType = AccountType.getType(Integer.parseInt(details[1])); // Account type passed as the int val of the enum
            this.unit = new OrganisationalUnit(Integer.parseInt(details[2])); // Get organisational unit from the orgUnitId
        }
        else {
            this.username = null;
            this.accountType = null;
            this.unit = null;
        }
    }

    /**
     * @return User's Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The user's account type (Member or Admin).
     */
    public AccountType getAccountType() { return accountType; }

    /**
     * @return The unit the user belongs to.
     */
    public OrganisationalUnit getOrganisationalUnit() {
        return unit;
    }

    /**
     * @return The user's userId
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Changes the user's password.
     *
     * @param currentHashedPassword The user's current password.
     * @param newHashedPassword The user's new password.
     * @return True if the password was successfully changed.
     */
    public boolean ChangePassword(String currentHashedPassword, String newHashedPassword){
        // Attempt to change the user's password
        Request response = null;
        try {
            response = NetworkManager.GetResponse("JDBCUserDataSource", "changePassword",
                    new String[] {String.valueOf(userID), currentHashedPassword, newHashedPassword});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response != null && response.getArguments() != null)
            return Boolean.parseBoolean(response.getArguments()[0]);
        else
            return false;
    }

}
