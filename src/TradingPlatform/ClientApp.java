package TradingPlatform;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) throws IOException {
        testServerClient();
    }

    private static void testServerClient() throws IOException {
        OrganisationalUnit organisationalUnit = new OrganisationalUnit();
        organisationalUnit.getName(1);
    }
}
