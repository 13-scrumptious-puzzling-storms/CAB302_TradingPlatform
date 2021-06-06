package TradingPlatform.JDBCDataSources;

import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;

public class TestJDBCOrganisationAsset {
    static Connection connection;
    static JDBCOrganisationalUnit reconcileSource;


    @BeforeEach
    public void init() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalUnit(connection);
    }


}
