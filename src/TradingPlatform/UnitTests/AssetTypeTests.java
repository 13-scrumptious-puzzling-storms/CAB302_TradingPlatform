package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCAssetType;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class AssetTypeTests {

    static Connection connection;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
    }

    @Test
    public void getAssetNameTest(){
        int orgID = 1;
        String assetName = "pens";
        JDBCAssetType unit = new JDBCAssetType(connection);
        assert((unit.getAssetName(orgID).equals(assetName)));
    }

    @Test
    public void addAssetTypeNameTest(){
        String assetName = "Paper";
        JDBCAssetType unit = new JDBCAssetType(connection);
        int assetTypeID = unit.addAssetType(assetName);
        //assert(unit.getAssetName(assetTypeID).equals(assetName));
    }
}
