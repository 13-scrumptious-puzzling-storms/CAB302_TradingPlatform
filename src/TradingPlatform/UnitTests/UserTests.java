package TradingPlatform.UnitTests;

import TradingPlatform.Interfaces.UserDataSource;
import TradingPlatform.NetworkProtocol.DBConnection;
import TradingPlatform.Server.JDBCUserDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class UserTests {
    static Connection connection;

    @BeforeAll
    public static void init(){
        connection = DBConnection.getInstance();
    }

    @Test
    public void TestGetUsername(){
        JDBCUserDataSource user = new JDBCUserDataSource(1, connection);
        System.out.println(user.getUsername());
    }
}
