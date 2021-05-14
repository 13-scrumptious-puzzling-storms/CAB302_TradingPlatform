package TradingPlatform;

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
     *
     * @param value The enum value
     */
    AccountType(final int value) {
        this.value = value;
    }

    /**
     * Gets the int value of an enum
     *
     * @return The enum's value.
     */
    public int getValue() {
        return value;
    }
}
