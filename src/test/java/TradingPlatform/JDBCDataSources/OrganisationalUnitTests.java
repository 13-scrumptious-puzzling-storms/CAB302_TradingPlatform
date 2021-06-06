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
    @Test
    public void ServerInstanceTest(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
    }

    @Test
    public void ServerAddOrganisationalUnit(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("New Organisation", 10) == 6);
    }

    @Test
    public void ServerAddOrganisationalUnitError(){
        //unit name already exists
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("Storms", 0) == -1);
    }

    @Test
    public void ServerGetOrganisationalUnitName(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(1).equals("ITAdmin"));
    }

    @Test
    public void ServerGetOrganisationalUnitName2(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(5).equals("Storms"));
    }

    @Test
    public void ServerGetOrganisationalUnitNameError(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(-1) == null);
    }

    @Test
    public void getOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(1) == 1000);
    }

    @Test
    public void getOrganisationalUnitCredits2(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(4) == 500);
    }

    @Test
    public void ServerGetOrganisationalUnit(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(1);
        assert (unit != null);
        assert (unit.getName().equals("ITAdmin"));
        assert (unit.getCredits() == 1000);
    }

    @Test
    public void ServerGetOrganisationalUnit2(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(5);
        assert (unit != null);
        assert (unit.getName().equals("Puzzling"));
        assert (unit.getCredits() == 500);
    }

    @Test
    public void ServerGetOrganisationalUnitError(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(-1);
        assert (unit == null);
    }

    @Test
    public void ServerUpdateOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(2, 255);
        assert(successful == true);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(2);
        assert(unit.getCredits() == 255);
    }

    @Test
    public void ServerUpdateOrganisationalUnitCreditsError(){
        //organisational unit ID invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(-1, 250);
        System.out.println(successful);
        assert(successful == false);
    }

    @Test
    public void ServerUpdateOrganisationalUnitCreditsError2(){
        //updated credits invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(1, -250);
        assert(successful == false);
    }

    @Test
    public void ServerGetAllOrganisationalUnits(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        String[][] result = unitSource.getAllOrganisationalUnits();
        assert(result != null);
        assert(result.length >= 5);
        //first row
        assert(result[0][0].equals("1"));
        assert(result[0][1].equals("ITAdmin"));
        assert(result[0][2].equals("1000"));
        //last row
        assert(result[4][0].equals("5"));
        assert(result[4][1].equals("Storms"));
        assert(result[4][2].equals("50"));
    }

    //------


}
