package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCTradeReconcileSource;
import TradingPlatform.JDBCDataSources.JDBCUserDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.Trade;
import TradingPlatform.TradeReconciliation.TradeOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

public class ReconcileTests {

    static Connection connection;
    static JDBCTradeReconcileSource reconcileSource;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
        reconcileSource = new JDBCTradeReconcileSource(connection);
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
