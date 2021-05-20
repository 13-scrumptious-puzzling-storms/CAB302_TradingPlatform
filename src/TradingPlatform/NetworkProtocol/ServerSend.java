package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerSend {
    public static void main(String[] args) throws IOException {
        OrganisationalUnitServer serialData = new OrganisationalUnitServer();
        serialData.setCredits(13);
        serialData.setId(14);

        File filename = new File("mySerialData");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            objectOutputStream.writeObject(serialData);
        }
    }
}
