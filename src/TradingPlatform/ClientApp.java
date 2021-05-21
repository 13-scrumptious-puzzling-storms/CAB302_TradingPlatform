package TradingPlatform;

import java.io.IOException;

public class ClientApp {

    // So you wanna talk to the serevr huh?!
    // use this bad boy
    public static NetworkManager networkManager;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        initialiseClientSend();
        testServerClient();
        new GUItester();
    }

    private static void initialiseClientSend() throws IOException { networkManager = new NetworkManager(); }

    private static void testServerClient() throws IOException, ClassNotFoundException {
        OrganisationalUnit organisationalUnit = new OrganisationalUnit();
        System.out.println("Organisational Unit Name for ID 1: " + organisationalUnit.getName(1));
    }
}
