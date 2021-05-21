package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.NetworkProtocol.DBConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class OrganisationAssetTests {


    static Connection connection;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
    }

    @Test
    public void addOrganisationAsset(){
        int orgID = 1;
        int assetID = 3;
        int quantity = 465;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        int orgAssetID = unit.addOrganisationAsset(orgID, assetID, quantity);
        System.out.println(orgAssetID);
        System.out.println(unit.getOrganisationAssetOrgID(orgAssetID));
        System.out.println(unit.getOrganisationAssetTypeID(orgAssetID));
        System.out.println(unit.getOrganisationAssetQuantity(orgAssetID));
        assert((unit.getOrganisationAssetOrgID(orgAssetID) == orgID) & (unit.getOrganisationAssetTypeID(orgAssetID) == assetID)
        & (unit.getOrganisationAssetQuantity(orgAssetID) == quantity));
    }


}
