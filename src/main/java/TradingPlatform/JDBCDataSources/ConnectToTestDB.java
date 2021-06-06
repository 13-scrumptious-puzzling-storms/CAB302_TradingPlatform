package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.JDBCDataSources.MockDatabaseFunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConnectToTestDB {
    private static final String TEST_PROPS_FILE = "./db_testdb.props";

    /**
     * Creates mock database and .props file for
     * DBConnection to use to connect to mock database.
     */
    public static void NewTestConnection() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TEST_PROPS_FILE))) {
            bufferedWriter.write("jdbc.url=jdbc:sqlite:testdb.db\njdbc.schema=\njdbc.username=\njdbc.password=");
        } catch (IOException e) { }
        MockDatabaseFunctions.InitDb();
        DBConnection.setPropsFile(TEST_PROPS_FILE);
    }

    /**
     * Deletes the mock database and deletes the .props file
     * referencing it.
     */
    public static void CloseTestConnection() {
        MockDatabaseFunctions.CloseDatabase();
        (new File(TEST_PROPS_FILE)).delete();
    }
}
