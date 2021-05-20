package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerApp {

    public static void main(String[] args) {
        ServerHandle serverHandleThread = new ServerHandle();
        serverHandleThread.start();
    }

    public static void shutdown() {
        // terminate all instances / get them to finish what they're doing
        // perhaps turn of the request handler
        System.exit(0);
    }
}
