package TradingPlatform;

import java.io.IOException;

public class ClientApp {

    public static NetworkManager networkManager;
    private static GUILogin guiLogin;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Initialise the client-side network protocol
        networkManager = new NetworkManager();
        Thread networkThread = new Thread(networkManager);
        networkThread.start();

        //networkTest();

        guiLogin = new GUILogin();
        Thread guiLoginThread = new Thread(guiLogin);
        guiLoginThread.start();

        //new GUIMain();
    }

    private static void networkTest() throws IOException, ClassNotFoundException {
        OrganisationalUnit organisationalUnit = new OrganisationalUnit();
        System.out.println("Organisational Unit Name for ID 1: " + organisationalUnit.getName(1));

        OrganisationAsset oAss = new OrganisationAsset();
        oAss.getOrganisationalUnitAssetTable(1);
    }
}
