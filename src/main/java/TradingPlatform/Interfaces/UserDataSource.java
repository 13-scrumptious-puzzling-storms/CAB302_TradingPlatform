package TradingPlatform.Interfaces;

import TradingPlatform.AccountType;
import TradingPlatform.OrganisationalUnit;

public interface UserDataSource {
    /**
     * @return User's username, or null on error or if not found.
     */
    String getUsername();

    /**
     * @return The user's account type (Member or Admin).
     */
    AccountType getAccountType();

    /**
     * @return The unit the user belongs to.
     */
    OrganisationalUnit getOrganisationalUnit();

    /**
     * Changes the user's password.
     *
     * @param currentHashedPassword The user's current password.
     * @param newHashedPassword The user's new password.
     * @return True if the password was successfully changed.
     */
    boolean ChangePassword(String currentHashedPassword, String newHashedPassword);
}
