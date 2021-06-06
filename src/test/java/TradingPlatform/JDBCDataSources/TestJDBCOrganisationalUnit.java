package TradingPlatform.JDBCDataSources;

import TradingPlatform.OrganisationalUnit;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestJDBCOrganisationalUnit {

    static Connection connection;
    static JDBCOrganisationalUnit reconcileSource;


//    @BeforeEach
    public void init() {
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
        reconcileSource = new JDBCOrganisationalUnit(connection);
    }


    @Test
    public void testServerInstance(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
    }

    @Test
    public void testServerAddOrganisationalUnit(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("New Organisation", 10) == 6);
        init();
    }

    // Exceptional case
    @Test
    public void testServerAddOrganisationalUnitError(){
        init();
        //unit name already exists
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.addOrganisationalUnit("Storms", 0) == -1);
    }

    // Boundary, getting name from first row.
    @Test
    public void testServerGetOrganisationalUnitName(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(1).equals("ITAdmin"));
    }

    // Boundary, getting name from last row.
    @Test
    public void testServerGetOrganisationalUnitName2(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(5).equals("Storms"));
    }

    // Boundary, getting name from invalid row.
    @Test
    public void testServerGetOrganisationalUnitNameError(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert (unit.getOrganisationalUnitName(-1) == null);
    }

    // Boundary, getting credits from first row.
    @Test
    public void testGetOrganisationalUnitCredits(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(1) == 1000);
    }

    // Boundary, getting credits from second-last row.
    @Test
    public void testGetOrganisationalUnitCredits2(){
        init();
        JDBCOrganisationalUnit unit = new JDBCOrganisationalUnit(connection);
        assert(unit.getOrganisationalUnitCredits(4) == 500);
    }

    // Boundary, getting organisational unit from first row.
    @Test
    public void testServerGetOrganisationalUnit(){
        init();
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(1);
        assert (unit != null);
        assert (unit.getName().equals("ITAdmin"));
        assert (unit.getCredits() == 1000);
    }

    // Boundary, getting organisational unit from last row.
    @Test
    public void testServerGetOrganisationalUnit2(){
        init();
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(5);
        assert (unit != null);
        System.out.println(unit.getName());
        assert (unit.getName().equals("Storms"));
        assert (unit.getCredits() == 50);
    }

    // Boundary, getting organisational unit from invalid row.
    @Test
    public void testServerGetOrganisationalUnitError(){
        init();
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(-1);
        assert (unit == null);
    }

    @Test
    public void testServerUpdateOrganisationalUnitCredits(){
        init();
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(2, 255);
        assert(successful == true);
        OrganisationalUnit unit = unitSource.getOrganisationalUnit(2);
        assert(unit.getCredits() == 255);
        init();
    }

    @Test
    public void testServerUpdateOrganisationalUnitCreditsError(){
        init();
        //organisational unit ID invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(-1, 250);
        assert(successful == false);
        init();
    }

    @Test
    public void testServerUpdateOrganisationalUnitCreditsError2(){
        init();
        //updated credits invalid
        JDBCOrganisationalUnit unitSource = new JDBCOrganisationalUnit(connection);
        Boolean successful = unitSource.UpdateOrganisationalUnitCredits(1, -250);
        assert(successful == false);
        init();
    }

    @Test
    public void testServerGetAllOrganisationalUnits(){
        init();
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
