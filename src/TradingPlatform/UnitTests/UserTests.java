package TradingPlatform.UnitTests;

import TradingPlatform.AccountType;
import TradingPlatform.JDBCDataSources.JDBCUserDataSource;
import TradingPlatform.SHA256;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class UserTests {
    static Connection connection;

    @BeforeAll
    public static void init(){
        connection = TestDatabaseFunctions.getConnection();
    }

    @AfterAll
    public static void deleteDb(){
        TestDatabaseFunctions.CloseDatabase();
    }

    @Test
    public void TestGetUserId(){
        int userId = JDBCUserDataSource.getUserId("markgrayson",  SHA256.hashPassword("Invincible"), connection);
        assert (userId == 5);
    }

    @Test
    public void TestGetUsername(){
        JDBCUserDataSource user = new JDBCUserDataSource(5, connection);
        assert (user.getUsername().equals("markgrayson"));
    }

    @Test
    public void TestGetAccountType(){
        JDBCUserDataSource INVINCIBLE = new JDBCUserDataSource(5, connection);
        assert(INVINCIBLE.getAccountType() == AccountType.MEMBER);

        JDBCUserDataSource Radmin = new JDBCUserDataSource(1, connection);
        assert(Radmin.getAccountType() == AccountType.ADMINISTRATOR);
    }

    @Test
    public void TestGetOrganisationalUnit(){
        JDBCUserDataSource user = new JDBCUserDataSource(1, connection);
        var orgUnit = user.getOrganisationalUnit();
        assert(orgUnit.getID() == 1);
    }
}
