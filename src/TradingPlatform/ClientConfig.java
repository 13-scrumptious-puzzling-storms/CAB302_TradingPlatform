package TradingPlatform;

import java.io.*;

public class ClientConfig {
    private static final int ADDRESS_FILE_LINES = 2;
    private static final String ADDRESS_FILE = "./server-address.txt";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final int DEFAULT_PORT = 2197;

    private static String ipAddress;
    private static int port;

    public static void SetIPAddress(String _ipAddress) {
        ipAddress = _ipAddress;
    }

    public static void SetPort(int _port) {
        port = _port;
    }

    public static String GetIPAddress() {
        return ipAddress;
    }

    public static int GetPort() {
        return port;
    }

    public static void ReadServerAddress() {
        CheckDefaultFile();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(ADDRESS_FILE))) {
            String[] lines = new String[ADDRESS_FILE_LINES];

            for (int i = 0; i < ADDRESS_FILE_LINES; i++) {
                lines[i] = bufferedReader.readLine();
            }

            ipAddress = lines[0];
            port = Integer.parseInt(lines[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void WriteServerAddress() {
        System.out.println(ipAddress);
        System.out.println(port);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ADDRESS_FILE))){
            bufferedWriter.write(ipAddress + "\n" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void SetDefaultAddress() {
        ipAddress = DEFAULT_IP;
        port = DEFAULT_PORT;
        WriteServerAddress();
    }

    private static void CheckDefaultFile() {
        if (!(new File(ADDRESS_FILE).exists())) {
            SetDefaultAddress();
        }
    }
}
