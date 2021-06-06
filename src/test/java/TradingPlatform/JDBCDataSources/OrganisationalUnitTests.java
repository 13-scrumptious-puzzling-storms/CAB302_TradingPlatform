package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationAsset;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

//
public class OrganisationalUnitTests {

    static Connection connection;


    @BeforeAll
    public static void init() {
        ConnectToTestDB.NewTestConnection();
        connection = DBConnection.getInstance();
    }

    OrganisationalUnit org1;
    OrganisationalUnit org2;
    OrganisationalUnit org3;

    @Test
    public void emptyCaseOrg(){
        org1 = new OrganisationalUnit();
    }

    @Test
    public void baseCaseOrg(){
        org2 = new OrganisationalUnit("CAB302", 200);
    }

    @Test
    public void baseCaseOrg2(){
        org2 = new OrganisationalUnit("CAB302", 0);
    }

    @Test
    public void twoBaseCaseOrg(){
        org2 = new OrganisationalUnit("TestCase 1", 200);
        org3 = new OrganisationalUnit("TestCase 2", 500);
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
        //organisational unit ID invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(1, -250);
        assert(successful == false);
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
    public void UpdateOrganisationalUnitCredits1(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        unit.UpdateOrganisationalUnitCredits( 1, 1276);
    }


    @Test
    public void UpdateOrganisationalUnitCredits2(){
        int credits = 230;
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        unit.UpdateOrganisationalUnitCredits( 1, credits);
        assert(unit.getOrganisationalUnitCredits(1) == credits);
    }



    @Test
    public void addOrganisationalUnit(){
        String orgName = "Testing org unit18";
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        int orgID = unit.addOrganisationalUnit(orgName, 237);
        assert((unit.getOrganisationalUnitName(orgID).equals(orgName)));
    }

    @Test
    public void getAssets() {
        OrganisationalUnit unit = new OrganisationalUnit(2);
        ArrayList<OrganisationAsset> response = unit.getAssets();
        System.out.println("test response is: " + response);
    }

}
