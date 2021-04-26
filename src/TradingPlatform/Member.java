package TradingPlatform;

/**
 * A member is a regular employee that belongs to an Organisational Unit.
 */
public class Member extends User {
    /**
     * Instantiates a member from their userid, getting their information from the database
     * @param userID The user's userid
     */
    public Member(int userID) {
        super(userID);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.MEMBER;
    }
}
