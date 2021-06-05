package TradingPlatform;

import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.UnitTests.TestDatabaseFunctions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConnectToTestDB {
    private static final String TEST_PROPS_FILE = "./db_testdb.props";

    public static void NewTestConnection() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TEST_PROPS_FILE))) {
            bufferedWriter.write("jdbc.url=jdbc:sqlite:testdb.db\njdbc.schema=\njdbc.username=\njdbc.password=");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestDatabaseFunctions.InitDb();
        DBConnection.setPropsFile(TEST_PROPS_FILE);
    }
}
