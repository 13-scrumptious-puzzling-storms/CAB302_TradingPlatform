package TradingPlatform.NetworkProtocol;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class TestDBConnection {
    Connection connection;

    @Test
    public void testConnection() {
        connection = DBConnection.getInstance();
    }
}
