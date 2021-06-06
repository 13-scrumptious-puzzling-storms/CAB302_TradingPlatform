package TradingPlatform;

import TradingPlatform.ClientApp;
import TradingPlatform.JDBCDataSources.ConnectToTestDB;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.JDBCDataSources.MockDatabaseFunctions;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.NetworkProtocol.ServerApp;
import TradingPlatform.OrganisationAsset;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

//
public class OrganisationalUnittes {

    static Connection connection;
    static JDBCOrganisationalUnit reconcileSource;

    @BeforeAll
    public static void testSetUp() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalUnit(connection);
        ClientApp.loggedIn = true;
    }

    @AfterAll
    public static void end() {
        ConnectToTestDB.CloseTestConnection();
    }

    OrganisationalUnit org1;
    OrganisationalUnit org2;
    OrganisationalUnit org3;

    @Test
    public void testEmptyCaseOrg(){
        org1 = new OrganisationalUnit();
        assert(org1.getName().equals(""));
        assert(org1.getCredits() == 0);
    }

    @Test
    public void testBaseCaseOrg(){
        org2 = new OrganisationalUnit("CAB302", 200);
        assert(org2.getName().equals("CAB302"));
        assert(org2.getCredits() == 200);
    }

    @Test
    public void testBaseCaseOrg2(){
        org2 = new OrganisationalUnit("CAB302", 0);
        assert(org2.getName().equals("CAB302"));
        assert(org2.getCredits() == 0);
    }

    @Test
    public void testTwoBaseCaseOrg(){
        org2 = new OrganisationalUnit("TestCase1", 200);
        org3 = new OrganisationalUnit("TestCase2", 500);
        assert(org2.getName().equals("TestCase1"));
        assert(org2.getCredits() == 200);
        assert(org3.getName().equals("TestCase2"));
        assert(org3.getCredits() == 500);
    }

    @Test
    public void testBaseCaseID1(){
//        ServerApp.main(new String[] {"testMode"});
//        org2 = new OrganisationalUnit(1);
//        assert(org2.getID() == 1);
//        assert(org2.getName().equals("ITAdmin"));
//        assert(org2.getCredits() == 1000);
    }

    @Test
    public void testSetID(){
        org2 = new OrganisationalUnit(2);
        org2.setId(7);
        assert(org2.getID() == 7);
    }

    @Test
    public void testGetID(){
        org2 = new OrganisationalUnit(1);
        assert(org2.getID() == 1);
    }

    @Test
    public void testGetID2(){
        org2 = new OrganisationalUnit(5);
        assert(org2.getID() == 5);
    }

    @Test
    public void testSetName(){
        org2 = new OrganisationalUnit(2);
        org2.setName("Testing");
        assert(org2.getName().equals("Testing"));
    }

    @Test
    public void testGetName1(){
//        org2 = new OrganisationalUnit(1);
//        assert(org2.getName().equals("ITAdmin"));
    }

    @Test
    public void testGetName2(){
//        org2 = new OrganisationalUnit(1);
//        assert(org2.getName(1).equals("ITAdmin"));
    }

    @Test
    public void testSetCredits(){
        org2 = new OrganisationalUnit(1);
        org2.setCredits(123);
        assert(org2.getCredits() == 123);
    }

    @Test
    public void testUpdateOrganisationalUnitCredits(){
        Boolean successful = OrganisationalUnit.UpdateOrganisationalUnitCredits(2, 30);
        assert(successful == true);
        assert(org2.getCredits(2) == 30);
    }

    @Test
    public void testUpdateOrganisationalUnitCreditsError(){
        Boolean successful = OrganisationalUnit.UpdateOrganisationalUnitCredits(-1, 30);
        assert(successful == false);
    }

    @Test
    public void testUpdateOrganisationalUnitCreditsError2(){
        Boolean successful = OrganisationalUnit.UpdateOrganisationalUnitCredits(2, -1);
        assert(successful == false);
    }

    @Test
    public void testGetCredits(){
        int credits = OrganisationalUnit.getCredits(1 );
        assert(credits == 1000);
    }

    @Test
    public void testGetCredits2(){
        int credits = OrganisationalUnit.getCredits(4 );
        assert(credits == 500);
    }

    @Test
    public void testGetCreditsError(){
        int credits = OrganisationalUnit.getCredits(-1 );
        assert(credits == -1);
    }
}
