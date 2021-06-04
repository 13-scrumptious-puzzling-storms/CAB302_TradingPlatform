package TradingPlatform.NetworkProtocol;

import javax.swing.*;
import java.io.IOException;

public class ServerApp {

    private static ServerHandle serverHandleRunnable;
    public static ServerSend serverSendRunnable;

    private static GUIManager GUIManagerRunnable;

    public static void main(String[] args) {
        System.out.println("Starting Server ...");

        serverHandleRunnable = new ServerHandle();
        Thread threadHandle = new Thread(serverHandleRunnable);
        threadHandle.start();

        serverSendRunnable = new ServerSend();
        Thread threadSend = new Thread(serverSendRunnable);
        threadSend.start();

        // NEED TO CHECK HOW TO PROPERLY START GUI. Do I use the invoke later below?
        GUIManagerRunnable = new GUIManager();
        Thread threadGUI = new Thread(GUIManagerRunnable);
        threadGUI.start();

        //SwingUtilities.invokeLater(new Runnable()
        //{
        //    public void run()
        //    {
        //        GUIManagerRunnable = new GUIManager();
        //    }
        //});
    }

    public static void shutdown() {
        System.out.println("\nShutting down Server ...");
        // terminate all instances / get them to finish what they're doing
        // perhaps turn of the request handler
        serverHandleRunnable.end();
        // serverSendRunnable.end(); shouldn't be needed since it is not in a while loop. there is no boolean to flip anyway
        System.exit(0);
        //could have sytem.exit after try block in thread
        // once bool is swithced AND thread finises what it was doing
        // then systrm .exit can be called
        // or maybe shutdown() can be called after the try block finishes?
    }
}
