package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerApp {

    private static ServerHandle serverHandleRunnable;
    public static ServerSend serverSendRunnable;

    public static void main(String[] args) {
        System.out.println("Starting Server ...");

        serverHandleRunnable = new ServerHandle();
        Thread threadHandle = new Thread(serverHandleRunnable);
        threadHandle.start();

        serverSendRunnable = new ServerSend();
        Thread threadSend = new Thread(serverSendRunnable);
        threadSend.start();
    }

    public static void shutdown() {
        // terminate all instances / get them to finish what they're doing
        // perhaps turn of the request handler
        serverHandleRunnable.end();
        //System.exit(0);
        //could have sytem.exit after try block in thread
        // once bool is swithced AND thread finises what it was doing
        // then systrm .exit can be called
        // or maybe shutdown() can be called after the try block finishes?
    }
}
