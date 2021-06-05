package TradingPlatform.NetworkProtocol;

import javax.swing.*;
import java.io.IOException;

public class ServerApp {

    private static Thread threadHandle;

    public static void main(String[] args) {
        System.out.println("Starting Server ...");

        // Start the Client requests handler for the Server.
        var serverHandleRunnable = new ServerHandle();
        threadHandle = new Thread(serverHandleRunnable);
        threadHandle.start();

        // Start the Server GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIManager();
            }
        });
    }

    public static void shutdown1() {
        System.out.println("\nShutting down Server ...");
        ServerHandle.end(); // Stop accepting requests
        try {
            ServerHandle.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            threadHandle.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.exit(0);
    }

    public static void shutdown() {
        System.out.println("\nShutting down Server ...");
        ServerHandle.end(); // Stop accepting requests
        while (ServerHandle.threadsOpen());
        System.exit(0);
    }
}
