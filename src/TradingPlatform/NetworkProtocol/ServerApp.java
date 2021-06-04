package TradingPlatform.NetworkProtocol;

import TradingPlatform.GUILogin;

import javax.swing.*;
import java.io.IOException;

public class ServerApp {

    private static ServerHandle serverHandleRunnable;
    public static ServerSend serverSendRunnable;

    public static void main(String[] args) {
        System.out.println("Starting Server ...");

        // Start the Client requests handler for the Server.
        serverHandleRunnable = new ServerHandle();
        Thread threadHandle = new Thread(serverHandleRunnable);
        threadHandle.start();

        // Start the Client reply component of the network protocol.
        serverSendRunnable = new ServerSend();
        Thread threadSend = new Thread(serverSendRunnable);
        threadSend.start();

        // Start the Server GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUIManager();
            }
        });
    }

    public static void shutdown() {
        System.out.println("\nShutting down Server ...");
        serverHandleRunnable.end(); // Stop accepting requests
        while (serverSendRunnable.isWorking) { } // Wait for ServerSend to finish
        System.exit(0);
    }
}
