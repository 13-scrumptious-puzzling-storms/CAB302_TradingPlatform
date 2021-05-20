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
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(1, connection);
        assert (unit.getOrganisationalUnitName() == "Test");
    }

    @Test
    public void ServerGetOrganisationalUnit(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(1, connection);
        assert (unit.getOrganisationalUnit() != null);
    }

    //Assert no exceptions?
    @Test
    public void UpdateOrganisationalUnitCredits1(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(1, connection);
        unit.UpdateOrganisationalunitCredits(4, 1276);
    }

    //Assert no exceptions?
    @Test
    public void UpdateOrganisationalUnitCredits2(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(1, connection);
        unit.UpdateOrganisationalunitCredits(1, 400);
    }

}
