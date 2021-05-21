package TradingPlatform.UnitTests;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

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
        org2 = new OrganisationalUnit("Shanelle", 200);
        System.out.println(org2.getID());
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
        unit.UpdateOrganisationalunitCredits( 1, 1276);
    }


    @Test
    public void UpdateOrganisationalUnitCredits2(){
        int credits = 230;
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        unit.UpdateOrganisationalunitCredits( 1, credits);
        assert(unit.getOrganisationalUnitCredits(1) == credits);
    }

    @Test
    public void getOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(1) == 230);
    }

    @Test
    public void addOrganisationalUnit(){
        String orgName = "Testing org unit7";
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        int orgID = unit.addOrganisationalUnit(orgName, 236);
        assert((unit.getOrganisationalUnitName(orgID).equals(orgName)));
    }


}
