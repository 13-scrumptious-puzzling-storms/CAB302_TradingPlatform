package TradingPlatform.NetworkProtocol;

import java.sql.Connection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDBConnection {
    Connection connection;

    @Test
    public void testConnection() {
        connection = DBConnection.getInstance();
    }
}
