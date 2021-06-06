package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationAsset;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

//
public class OrganisationalUnitTests {

    static Connection connection;


    @BeforeAll
    public static void init() {
        ConnectToTestDB.NewTestConnection();
        connection = DBConnection.getInstance();
    }

    @AfterAll
    public static void end() {
        ConnectToTestDB.CloseTestConnection();
    }

    OrganisationalUnit org1;
    OrganisationalUnit org2;
    OrganisationalUnit org3;

    @Test
    public void emptyCaseOrg(){
        org1 = new OrganisationalUnit();
        assert(org1.getName().equals(""));
        assert(org1.getCredits() == 0);
    }

    @Test
    public void baseCaseOrg(){
        org2 = new OrganisationalUnit("CAB302", 200);
        assert(org2.getName().equals("CAB302"));
        assert(org2.getCredits() == 200);
    }

    @Test
    public void baseCaseOrg2(){
        org2 = new OrganisationalUnit("CAB302", 0);
        assert(org2.getName().equals("CAB302"));
        assert(org2.getCredits() == 0);
    }

    @Test
    public void twoBaseCaseOrg(){
        org2 = new OrganisationalUnit("TestCase1", 200);
        org3 = new OrganisationalUnit("TestCase2", 500);
        assert(org2.getName().equals("TestCase1"));
        assert(org2.getCredits() == 200);
        assert(org3.getName().equals("TestCase2"));
        assert(org3.getCredits() == 500);
    }

    @Test
    public void baseCaseID1(){
        org2 = new OrganisationalUnit(1);
        assert(org2.getID() == 1);
        assert(org2.getName().equals("ITAdmin"));
        assert(org2.getCredits() == 1000);
    }



//------------ JDBCOrganisationalUnit tests

    //------


}
