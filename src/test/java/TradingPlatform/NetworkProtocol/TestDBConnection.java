package TradingPlatform.NetworkProtocol;

import TradingPlatform.JDBCDataSources.ConnectToTestDB;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestDBConnection {

    private Connection connection;

    @Test
    public void testSetGetPropsFile() {
        // Run the test
        DBConnection.setPropsFile("test");

        // Verify the results
        assert (DBConnection.getPropsFile() == "test");
    }

    @Test
    public void testGetIsConnectedFalse() {
        // Run the test
        DBConnection.setPropsFile("test");
        DBConnection.getInstance();

        // Verify the results
        assert (DBConnection.getIsConnected() == false);
    }

    @Test
    public void testGetInstance() {
        // Setup
        ConnectToTestDB.NewTestConnection();

        // Run the test
        connection = DBConnection.getInstance();

        // Verify the results
        assert (connection != null);
    }

    @Test
    public void testGetIsConnectedTrue() {
        // Setup
        ConnectToTestDB.NewTestConnection();

        // Run the test
        connection = DBConnection.getInstance();

        // Verify the results
        assert (DBConnection.getIsConnected() == true);
    }

    @Test
    public void testExceptionCaught() {
        // Run the test
        // Since no file set, this will throw, but catch exception.
        connection = DBConnection.getInstance();
        assert (connection == null);
    }
}
