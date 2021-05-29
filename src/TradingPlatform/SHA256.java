package TradingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    // Reference https://www.baeldung.com/sha-256-hashing-java
    public static String hashPasword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        String stringHash = new String(messageDigest.digest());
        return stringHash;
    }
}
