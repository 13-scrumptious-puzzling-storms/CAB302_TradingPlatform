package TradingPlatform.JDBCDataSources;

import TradingPlatform.AccountType;
import TradingPlatform.SHA256;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestJDBCUser {
    static Connection connection = MockDatabaseFunctions.getConnection();

    private void resetDB(){
        MockDatabaseFunctions.InitDb();
        connection = MockDatabaseFunctions.getConnection();
    }

    @AfterAll
    public static void deleteDb(){
        MockDatabaseFunctions.CloseDatabase();
    }

    @Test
    public void testGetUserId(){
        int userId = JDBCUserDataSource.getUserId("markgrayson",  SHA256.hashPassword("Invincible"), connection);
        assert (userId == 5);
    }

    @Test
    public void testGetUsername(){
        JDBCUserDataSource user = new JDBCUserDataSource(5, connection);
        assert (user.getUsername().equals("markgrayson"));
    }

    @Test
    public void testGetAccountType(){
        JDBCUserDataSource INVINCIBLE = new JDBCUserDataSource(5, connection);
        assert(INVINCIBLE.getAccountType() == AccountType.MEMBER);

        JDBCUserDataSource Radmin = new JDBCUserDataSource(1, connection);
        assert(Radmin.getAccountType() == AccountType.ADMINISTRATOR);
    }

    @Test
    public void testGetOrganisationalUnit(){
        JDBCUserDataSource user = new JDBCUserDataSource(1, connection);
        var orgUnit = user.getOrganisationalUnit();
        assert(orgUnit.getID() == 1);
    }

    @Test
    public void testAddUser(){
        // reset the db
        resetDB();
        boolean added = JDBCUserDataSource.addUser("testUser1", "password", AccountType.MEMBER, 1, connection);
        assert(added);
    }

    @Test
    public void testDoesNotAddDuplicateUser(){
        // Reset the db
        resetDB();
        JDBCUserDataSource.addUser("testUser1", "shouldAdd", AccountType.MEMBER, 1, connection);
        boolean added = JDBCUserDataSource.addUser("testUser1", "shouldNotAdd", AccountType.ADMINISTRATOR, 1, connection);
        // Should not be able to add user with a duplicate username
        assert(!added);
    }

    @Test
    public void testChangePassword(){
        // Test change password by changing radmin's password
        JDBCUserDataSource userSource = new JDBCUserDataSource(1, connection);
        boolean changed = userSource.ChangePassword(SHA256.hashPassword("password1"), "newPassword");
        assert (changed);
        int userId = JDBCUserDataSource.getUserId("radmin", "newPassword", connection);
        assert (userId == 1);
    }

    @Test
    public void testAdminChangeUserPassword(){
        // Test admin change password by changing nolangrayson's password
        boolean changed = JDBCUserDataSource.adminChangeUserPassword("nolangrayson", "newPassword", connection);
        assert (changed);
        int userId = JDBCUserDataSource.getUserId("nolangrayson", "newPassword", connection);
        assert (userId == 2);
    }
}
