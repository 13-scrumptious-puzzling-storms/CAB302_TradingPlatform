package TradingPlatform;

import java.util.HashMap;
import java.util.Map;

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
    private static final Map<Integer, AccountType> map = new HashMap<>();

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

    public static AccountType getType(int value){
        return map.get(value);
    }
    
    // Initialises the enum map
    static {
        for (AccountType typeEnum : AccountType.values()) {
            map.put(typeEnum.value, typeEnum);
        }
    }
}
