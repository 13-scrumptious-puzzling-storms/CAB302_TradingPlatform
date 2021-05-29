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
        // Get the logged in user
        User user = new User(1);
        new GUIMain(user);
    }

    private static void networkTest() throws IOException, ClassNotFoundException {
        OrganisationalUnit organisationalUnit = new OrganisationalUnit();
        System.out.println("Organisational Unit Name for ID 1: " + organisationalUnit.getName(1));

        OrganisationAsset oAss = new OrganisationAsset();
        oAss.getOrganisationalUnitAssetTable(1);
    }
}
