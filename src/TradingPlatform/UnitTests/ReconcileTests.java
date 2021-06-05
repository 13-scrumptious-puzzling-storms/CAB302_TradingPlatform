package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeReconcile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class ReconcileTests {

    static Connection connection;
    static JDBCTradeReconcileSource reconcileSource;

    @BeforeEach
    public void resetDb(){
        TestDatabaseFunctions.InitDb();
        connection = TestDatabaseFunctions.getConnection();
        reconcileSource = new JDBCTradeReconcileSource(connection);
    }

    @AfterAll
    public static void DeleteTestDb(){

        //TestDatabaseFunctions.CloseDatabase();
    }

    @Test
    public void TestGetReconcilableAssetTypes(){
        ArrayList<Integer> assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        assert (assetTypeIds != null);
        assert (assetTypeIds.size() > 0);
        System.out.println("Reconcilable AssetTypeIds:");
        for (Integer i : assetTypeIds){
            System.out.println(i);
        }
    }

    @Test
    public void TestGetSellOrders(){
        ArrayList<TradeOrder> sellOrders = reconcileSource.getCurrentSellOrders(3);
        assert (sellOrders != null);
        assert (sellOrders.size() > 0);
        System.out.println("Sell Orders for AssetType 3:");
        for (TradeOrder trade : sellOrders){
            System.out.println(trade.getTradeOrderId() + " " + trade.getOrganisationAssetId());
        }
    }

    @Test
    public void TestReconcile(){
        TradeReconcile.ReconcileCurrentTrades(connection);
        // Check that there are no reconcilable trades left
        var assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        assert (assetTypeIds.size() == 0);
    }
}
