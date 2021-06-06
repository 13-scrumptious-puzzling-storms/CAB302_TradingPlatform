package TradingPlatform;

import org.junit.jupiter.api.Test;

import java.io.File;

public class TestClientConfig {

    @Test
    public void testSetGetIPAddress() {
        // Run the test
        ClientConfig.SetIPAddress("_ipAddress");

        // Verify the results
        assert (ClientConfig.GetIPAddress().equals("_ipAddress"));
    }

    @Test
    public void testSetGetPort() {
        // Run the test
        ClientConfig.SetPort(0);

        // Verify the results
        assert (ClientConfig.GetPort() == 0);
    }

    @Test
    public void testWriteServerAddress() {
        // Setup

        // Run the test
        ClientConfig.WriteServerAddress();

        // Verify the results
    }

    @Test
    public void testReadServerAddress() {
        // Run the test
        ClientConfig.SetDefaultAddress();
        ClientConfig.ReadServerAddress();

        // Verify the results
        assert (ClientConfig.GetIPAddress().equals("127.0.0.1") && ClientConfig.GetPort() == 2197);
    }

    @Test
    public void testFileCreation() {
        (new File("./server-address.txt")).delete();

        ClientConfig.ReadServerAddress();
    }
}
