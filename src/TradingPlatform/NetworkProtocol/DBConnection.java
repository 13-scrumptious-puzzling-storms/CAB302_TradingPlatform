package TradingPlatform.NetworkProtocol;

import java.sql.Connection;

/**
 * Establishes a connection with the database.
 *
 * @author Malcolm Corney
 */
public class DBConnection {

    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance = null;

    /**
     * Constructor initialises the connection.
     */
    private DBConnection() {

    }

    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }
}
