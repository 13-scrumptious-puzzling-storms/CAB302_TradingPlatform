package TradingPlatform;

import java.io.IOException;

public class ClientApp {

    public static NetworkManager networkManager;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Initialise the client-side network protocol
        networkManager = new NetworkManager();
        Thread networkThread = new Thread(networkManager);
        networkThread.start();

        networkTest();
        new GUItester();
    }

    private static void networkTest() throws IOException, ClassNotFoundException {
        OrganisationalUnit organisationalUnit = new OrganisationalUnit();
        System.out.println("Organisational Unit Name for ID 1: " + organisationalUnit.getName(1));
    }
}
