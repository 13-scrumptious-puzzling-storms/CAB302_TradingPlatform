package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerHandle {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File filename = new File("mySerialData");

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));) {
            OrganisationalUnitServer serialData = (OrganisationalUnitServer) objectInputStream.readObject();
            System.out.println("serialData's ID: " + serialData.getID());
            System.out.println("serialData's Credits: " + serialData.getCredits());
        }
    }
}
