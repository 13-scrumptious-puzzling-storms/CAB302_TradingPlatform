package TradingPlatform.JDBCDataSources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

public class TestJDBCOrganisationAsset {
    static Connection connection;
    static JDBCOrganisationalAsset reconcileSource;


    @BeforeEach
    public void init() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalAsset(connection);
    }

    @Test
    public void testServerInstance(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
    }

    @Test
    public void testServerGetOrganisationAssetOrgUnitID(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetOrgUnitID(1) == 2);
    }

    @Test
    public void testServerGetOrganisationAssetOrgUnitIDError(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetOrgUnitID(-1) == -1);
    }

    @Test
    public void testGetOrganisationAssetsAndQuantity(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        String[][] assetsAndQuantity = orgAsset.getOrganisationAssetsAndQuantity(2);
        System.out.println(Arrays.deepToString(assetsAndQuantity));
        assert(assetsAndQuantity[0][0].equals("1"));
        assert(assetsAndQuantity[0][1].equals("Pen"));
        assert(assetsAndQuantity[0][2].equals("50"));

        assert(assetsAndQuantity[0][0].equals("2"));
        assert(assetsAndQuantity[0][1].equals("Printer"));
        assert(assetsAndQuantity[0][2].equals("200"));

        assert(assetsAndQuantity[0][0].equals("3"));
        assert(assetsAndQuantity[0][1].equals("Ruler"));
        assert(assetsAndQuantity[0][2].equals("100"));
    }

    @Test
    public void testGetOrganisationAssetQuantityError(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetQuantity(-1) == -1);
    }

    @Test
    public void testGetOrganisationTypeQuantity(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(1) == 1);
    }

    @Test
    public void testGetOrganisationAssetTypeError(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(-1) == -1);
    }

    @Test
    public void testGetOrganisationAssetsAndQuantityr(){
        JDBCOrganisationalAsset orgAsset = new JDBCOrganisationalAsset(connection);
        assert(orgAsset.getOrganisationAssetTypeID(-1) == -1);
    }


}
