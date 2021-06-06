package TradingPlatform.JDBCDataSources;

import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestJDBCOrganisationalUnit {

    static Connection connection;
    static JDBCOrganisationalUnit reconcileSource;


    @BeforeEach
    public void init() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalUnit(connection);
    }


    @Test
    public void testServerInstance(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
    }

    @Test
    public void testServerAddOrganisationalUnit(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("New Organisation", 10) == 6);
        init();
    }

    @Test
    public void testServerAddOrganisationalUnitError(){
        //unit name already exists
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("Storms", 0) == -1);
        init();
    }

    @Test
    public void testServerGetOrganisationalUnitName(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(1).equals("ITAdmin"));
    }

    @Test
    public void testServerGetOrganisationalUnitName2(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(5).equals("Storms"));
    }

    @Test
    public void testServerGetOrganisationalUnitNameError(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(-1) == null);
    }

    @Test
    public void testGetOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(1) == 1000);
    }

    @Test
    public void testGetOrganisationalUnitCredits2(){
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(4) == 500);
    }

    @Test
    public void testServerGetOrganisationalUnit(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(1);
        assert (unit != null);
        assert (unit.getName().equals("ITAdmin"));
        assert (unit.getCredits() == 1000);
    }

    @Test
    public void testServerGetOrganisationalUnit2(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(5);
        assert (unit != null);
        System.out.println(unit.getName());
        assert (unit.getName().equals("Storms"));
        assert (unit.getCredits() == 50);
    }

    @Test
    public void testServerGetOrganisationalUnitError(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(-1);
        assert (unit == null);
    }

    @Test
    public void testServerUpdateOrganisationalUnitCredits(){
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(2, 255);
        assert(successful == true);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(2);
        assert(unit.getCredits() == 255);
        init();
    }

    @Test
    public void testServerUpdateOrganisationalUnitCreditsError(){
        //organisational unit ID invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(-1, 250);
        System.out.println(successful);
        assert(successful == false);
        init();
    }

    @Test
    public void testServerUpdateOrganisationalUnitCreditsError2(){
        //updated credits invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(1, -250);
        assert(successful == false);
        init();
    }

    @Test
    public void testServerGetAllOrganisationalUnits(){
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

}
