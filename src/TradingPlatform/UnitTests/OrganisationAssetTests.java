package TradingPlatform.UnitTests;

import TradingPlatform.ConnectToTestDB;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationAsset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class OrganisationAssetTests {

    static Connection connection;

    @BeforeAll
    public static void init(){
        ConnectToTestDB.NewTestConnection();
        connection = DBConnection.getInstance();
    }

    @Test
    public void addOrganisationAssetTest(){
        int orgID = 1;
        int assetID = 3;
        int quantity = 466;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        int orgAssetID = unit.addOrganisationAsset(orgID, assetID, quantity);
        assert((unit.getOrganisationAssetOrgUnitID(orgAssetID) == orgID) & (unit.getOrganisationAssetTypeID(orgAssetID) == assetID)
        & (unit.getOrganisationAssetQuantity(orgAssetID) == quantity));
    }

    @Test
    public void getOrganisationAssetOrgUnitIDTest(){
        int orgAssetID = 10;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        assert(unit.getOrganisationAssetOrgUnitID(orgAssetID) == 3);
    }

    @Test
    public void getOrganisationAssetQuantityTest(){
        int orgAssetID = 10;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        assert(unit.getOrganisationAssetQuantity(orgAssetID) == 35);
    }

    @Test
    public void getOrganisationAssetTypeIDTest(){
        int orgAssetID = 10;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        assert(unit.getOrganisationAssetTypeID(orgAssetID) == 5);
    }

    @Test
    public void UpdateOrganisationAssetQuantityTest(){
        int orgAssetID = 10;
        int newQuantity = 36;
        JDBCOrganisationalAsset unit = new JDBCOrganisationalAsset(connection);
        unit.UpdateOrganisationAssetQuantity(orgAssetID, newQuantity);
        assert(unit.getOrganisationAssetQuantity(orgAssetID)== newQuantity);
    }


}
