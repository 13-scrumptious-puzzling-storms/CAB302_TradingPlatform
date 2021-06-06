package TradingPlatform.TradeReconciliation;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.JDBCDataSources.MockDatabaseFunctions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestReconcile {
    static Connection connection;
    static JDBCTradeReconcileSource reconcileSource;

    private void init(){
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCTradeReconcileSource(connection);
    }

    @Test
    public void testReconcile(){
        init();
        TradeReconcile.ReconcileCurrentTrades(connection);
        // Check that there are no reconcilable trades left
        var assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        assert (assetTypeIds.size() == 0);
    }

    @AfterAll
    public static void testAfterAll(){
        MockDatabaseFunctions.CloseDatabase();
    }
}
