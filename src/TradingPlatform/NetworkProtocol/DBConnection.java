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
 * This class is extended upon the work of
 * @author Malcolm Corney
 */
public class DBConnection {
    private String propsFile = "db.props";

    /**
     * The singleton instance of the database connection.
     */
    private static Connection instance;

    /**
     * Constructor initialises the connection.
     */
    private DBConnection() {
        Properties props = new Properties();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("db.props");
            props.load(inputStream);
            inputStream.close();

            // Specifying the data source, username and password.
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            String schema = props.getProperty("jdbc.schema");

            // Get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
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
