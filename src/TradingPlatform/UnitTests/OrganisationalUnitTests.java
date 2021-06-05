package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationAsset;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

//
public class OrganisationalUnitTests {

    static Connection connection;

    @BeforeAll
    public static void init(){
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
    public void twoBaseCaseOrg(){
        org2 = new OrganisationalUnit("Shanelle", 200);
        System.out.println(org2.getID());

        org3 = new OrganisationalUnit("Bella", 500);
        System.out.println(org3.getID());
    }

    @Test
    public void DataBaseConnection(){
        org2 = new OrganisationalUnit("Shanelle", 200);
        System.out.println(org2.getID());
    }

    @Test
    public void ServerGetOrganisationalUnitName(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(1).equals("Test"));
    }

    @Test
    public void ServerGetOrganisationalUnit(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnit(1) != null);
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
    public void getOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(1) >= 0);
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
