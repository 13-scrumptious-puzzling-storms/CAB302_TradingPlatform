package TradingPlatform;

/**
 * A user is a part of an organisational unit, and has their own username, password,
 * and account type.
 */
public abstract class User {
    private final OrganisationalUnit unit;
    private final String username;
    private int userID;

    /**
     * Instantiates a user from their userid, getting their information from the database
     * @param userID The user's userid
     */
    public User(int userID){
        this.unit = null;
        this.username = null;
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
    public abstract AccountType getAccountType();

    /**
     * @return The unit the user belongs to.
     */
    public OrganisationalUnit getOrganisationalUnit() {
        return unit;
    }

    /**
     * Changes the user's password.
     *
     * @param currentPassword The user's current password.
     * @param newPassword The user's new password.
     * @return True if the password was successfully changed.
     */
    public boolean ChangePassword(String currentPassword, String newPassword){
        return false;
    }

    /**
     * Enum of the different account types.
     */
    public enum AccountType {
        /**
         * A regular employee account.
         */
        MEMBER(0),
        /**
         * An IT Administrator account.
         */
        ADMINISTRATOR(1);

        private final int value;

        /**
         * Sets the enum value
         * @param value The enum value
         */
        AccountType(final int value) {
            this.value = value;
        }

        /**
         * Gets the int value of an enum
         * @return The enum's value.
         */
        public int getValue() { return value; }
    }
}
