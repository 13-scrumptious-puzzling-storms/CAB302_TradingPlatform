package TradingPlatform.JDBCDataSources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

public class TestJDBCOrganisationAsset {
    static Connection connection;
    static JDBCOrganisationalAsset reconcileSource;


//    @BeforeEach
    public void init() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalAsset(connection);
    }

    @Test
    public void testServerInstance(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
    }

    @Test
    public void testServerGetOrganisationAssetOrgUnitID(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetOrgUnitID(1) == 2);
    }

    @Test
    public void testServerGetOrganisationAssetOrgUnitIDError(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetOrgUnitID(-1) == -1);
    }

    @Test
    public void testGetOrganisationAssetsAndQuantity(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        String[][] assetsAndQuantity = orgAsset.getOrganisationAssetsAndQuantity(2);
        System.out.println(Arrays.deepToString(assetsAndQuantity));
        assert(assetsAndQuantity[0][0].equals("1"));
        assert(assetsAndQuantity[0][1].equals("Pen"));
        assert(assetsAndQuantity[0][2].equals("50"));

        assert(assetsAndQuantity[1][0].equals("2"));
        assert(assetsAndQuantity[1][1].equals("Printer"));
        assert(assetsAndQuantity[1][2].equals("200"));

        assert(assetsAndQuantity[2][0].equals("3"));
        assert(assetsAndQuantity[2][1].equals("Ruler"));
        assert(assetsAndQuantity[2][2].equals("100"));
    }

    @Test
    public void testGetOrganisationAssetQuantityError(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetQuantity(-1) == -1);
    }

    @Test
    public void testGetOrganisationTypeQuantity(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(1) == 1);
    }

    @Test
    public void testGetOrganisationAssetTypeError(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(-1) == -1);
    }

    @Test
    public void testGetOrganisationAssetsAndQuantityr(){
        init();
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(-1) == -1);
    }


}
