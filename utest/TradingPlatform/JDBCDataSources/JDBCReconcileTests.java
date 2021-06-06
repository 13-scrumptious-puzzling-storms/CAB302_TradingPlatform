package TradingPlatform.JDBCDataSources;

import TradingPlatform.TradeReconciliation.TradeOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class JDBCReconcileTests {

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
}
