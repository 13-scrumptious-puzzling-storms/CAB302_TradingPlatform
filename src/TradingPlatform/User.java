package TradingPlatform;

/**
 * A user is a part of an organisational unit, and has their own username, password,
 * and account type.
 */
public class User {
    private final OrganisationalUnit unit;
    private final String username;
    private final AccountType accountType;

    /**
     *
     * @param username User's username.
     * @param unit Unit that the user belongs to.
     */
    public User(String username, OrganisationalUnit unit){
        this.username = username;
        this.unit = unit;
        this.accountType = AccountType.MEMBER;
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
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @return The unit the user belongs to.
     */
    public OrganisationalUnit getOrganisationalUnit() {
        return unit;
    }

    /**
     * Changes the user's password.
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
        MEMBER(0),
        ADMINISTRATOR(1);

        private final int value;

        AccountType(final int value) {
            this.value = value;
        }

        public int getValue() { return value; }
    }
}
