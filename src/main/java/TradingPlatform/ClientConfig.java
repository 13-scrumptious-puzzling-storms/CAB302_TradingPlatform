package TradingPlatform;

import java.io.*;

/**
 * Responsible for setting, getting and saving the config relating
 * to the Server's IP Address and Port number.
 */
public class ClientConfig {
    private static final int ADDRESS_FILE_LINES = 2;
    private static final String ADDRESS_FILE = "./server-address.txt";
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final int DEFAULT_PORT = 2197;

    private static String ipAddress;
    private static int port;

    /**
     * Sets the String ipAddress to the String argument.
     * @param _ipAddress the String argument.
     */
    public static void SetIPAddress(String _ipAddress) { ipAddress = _ipAddress; }

    /**
     * Sets the int port to the int argument.
     * @param _port the int argument.
     */
    public static void SetPort(int _port) {
        port = _port;
    }

    /**
     * @return returns the String ipAddress.
     */
    public static String GetIPAddress() {
        return ipAddress;
    }

    /**
     * @return returns the int port.
     */
    public static int GetPort() {
        return port;
    }

    /**
     * Reads the Server Address info that is saved in a .txt file.
     * Sets fields to these read values.
     * Calls CheckDefaultFile() to check if .txt files exists.
     */
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

    /**
     * Writes fields to .txt file.
     * Creates the .txt if it doesn't exist.
     */
    public static void WriteServerAddress() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ADDRESS_FILE))){
            bufferedWriter.write(ipAddress + "\n" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the fields to default values and invokes
     * WriterServerAddress to write these to .txt.
     */
    public static void SetDefaultAddress() {
        ipAddress = DEFAULT_IP;
        port = DEFAULT_PORT;
        WriteServerAddress();
    }

    /**
     * Checks if the .txt file exists.
     */
    private static void CheckDefaultFile() {
        if (!(new File(ADDRESS_FILE).exists())) {
            SetDefaultAddress();
        }
    }
}
