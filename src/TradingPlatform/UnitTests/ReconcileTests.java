package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.TradeReconciliation.TradeOrder;
import TradingPlatform.TradeReconciliation.TradeReconcile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class ReconcileTests {

    static Connection connection;
    static JDBCTradeReconcileSource reconcileSource;
    static final Object tradeLock = new Object();
    static TradeReconcile tradeReconcile;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
        reconcileSource = new JDBCTradeReconcileSource(connection);
        tradeReconcile = new TradeReconcile(tradeLock);
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
        ArrayList<Integer> assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        // This is just to make sure that there is test data
        assert (assetTypeIds.size() > 0);
        tradeReconcile.ReconcileCurrentTrades();
        assetTypeIds = reconcileSource.getCurrentReconcilableAssetTypeIds();
        assert (assetTypeIds.size() == 0);
    }
}
