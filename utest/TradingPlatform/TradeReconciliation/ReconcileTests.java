package TradingPlatform.TradeReconciliation;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.JDBCDataSources.MockDatabaseFunctions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ReconcileTests {
    static Connection connection;
    static JDBCTradeReconcileSource reconcileSource;

    @BeforeEach
    public void resetDb(){
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCTradeReconcileSource(connection);
    }

    @AfterAll
    public static void DeleteTestDb(){
        MockDatabaseFunctions.CloseDatabase();
    }

    @Test
    public void TestReconcile(){
        TradeReconcile.ReconcileCurrentTrades(connection);
        // Check that there are no reconcilable trades left
        var assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        assert (assetTypeIds.size() == 0);
    }
}
