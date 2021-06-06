package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestJDBCAssetType {

    static Connection connection;
    static JDBCAssetType asset;

    @BeforeEach
    public void init(){
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        asset = new JDBCAssetType(connection);
    }

    @Test
    public void getAssetNameTest(){
        init();
        int assetID = 1;
        String assetName = "Pen";
        assert((asset.getAssetName(assetID).equals(assetName)));
    }

    @Test
    public void addAssetTypeNameTest(){
        init();
        String assetName = "Paper";
        JDBCAssetType unit = new JDBCAssetType(connection);
        int assetTypeID = unit.addAssetType(assetName);
        assert(unit.getAssetName(assetTypeID).equals(assetName));
    }

    @Test
    public void testGetAllAssetNames(){
        init();
        assert (asset.getAllAssetNames() != null);
    }

    @Test
    public void testGetAllAssetTypes(){
        init();
        assert (asset.getAllAssetTypes() != null);
    }

    @Test
    public void testGetAssetId(){
        init();
        String name = "Pen";
        assert (asset.getAssetId(name) == 1);
    }
}
