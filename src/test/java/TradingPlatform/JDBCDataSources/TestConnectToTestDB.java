package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestConnectToTestDB {

    @Test
    public void testNewTestConnection() {
        // Run the test
        ConnectToTestDB.NewTestConnection();

        // Verify the results
        assert (DBConnection.getInstance() != null && DBConnection.getIsConnected() == true);
    }

    @Test
    public void testCloseTestConnection() {
        // Run the test
        ConnectToTestDB.CloseTestConnection();

        // Verify the results
        Boolean propsDeleted = (new File("./db_testdb.props")).exists() == false;
        assert (propsDeleted);
    }
}
