package TradingPlatform.NetworkProtocol;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Establishes a connection with the database.
 * This class is based upon the work of
 * @author Malcolm Corney.
 */
public class DBConnection {
    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance;

    private static String propsFile = "./db_test.props";

    /**
     * Sets the instance to null (since the parameters to connect to DB will have changed).
     * Also sets the String propsFile to String file.
     * @param file the new String file.
     */
    public static void setPropsFile(String file) {
        propsFile = file;
        instance = null;
    }

    private static Boolean isConnected = false;

    /**
     * @return returns a Bool indicating whether DBConnection was able to
     * establish a connection to the database using the specified
     * .props parameters.
     */
    public static Boolean getIsConnected() { return isConnected; }

    /**
     * Constructor initialises the connection.
     * If connection is successful, Bool isConnected is set to true.
     */
    private DBConnection() {
        Properties props = new Properties();
        FileInputStream inputStream = null;
        isConnected = false;
        try {
            inputStream = new FileInputStream(propsFile);
            props.load(inputStream);
            inputStream.close();

            // Specifying the data source, username and password.
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // Get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username, password);
            isConnected = true;
            System.out.println("DBConnection: Successfully connected to database.");
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (!isConnected) {
            System.out.println("DBConnection: Failed to connect to database with current .props file!");
        }
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
