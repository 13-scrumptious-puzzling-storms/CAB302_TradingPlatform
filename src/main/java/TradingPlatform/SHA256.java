package TradingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes Strings and returns hashed Strings.
 */
public class SHA256 {
    /**
     * Hashes the provided String password using the SHA256 algorithm.
     * Returns the resulting hashed String.
     *
     * @param password The String password to hash.
     * @return The hashed String of the password.
     * @author https://www.baeldung.com/sha-256-hashing-java.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            String stringHash = new String(messageDigest.digest());
            return stringHash;
        }
        catch (NoSuchAlgorithmException ex) {
            // Algorithm does exist. This exception will never be thrown.
            ex.printStackTrace();
            return password;
        }
    }
}
