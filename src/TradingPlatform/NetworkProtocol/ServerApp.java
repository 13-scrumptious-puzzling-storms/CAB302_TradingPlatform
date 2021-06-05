package TradingPlatform.NetworkProtocol;

import TradingPlatform.TradeReconciliation.TradeReconcile;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerApp {

    private static Thread threadHandle;
    public static ScheduledExecutorService serverReconcileExecutor;

    private static final int RECONCILE_TIMER = 5; // how many seconds between reconciling trades

    public static void main(String[] args) {
        System.out.println("Starting Server ...");

        // Start the Client requests handler for the Server.
        var serverHandleRunnable = new ServerHandle();
        threadHandle = new Thread(serverHandleRunnable);
        threadHandle.start();

        // Start the reconcile thread, and make it run every 5 seconds
        try {
            serverReconcileExecutor = Executors.newSingleThreadScheduledExecutor();
            serverReconcileExecutor.scheduleAtFixedRate(() -> {
                if (DBConnection.getIsConnected()) {
                    Connection conn = DBConnection.getInstance();
                    // run the trade reconcile thread
                    if (conn != null)
                        TradeReconcile.ReconcileCurrentTrades(conn);
                }
            }, 0, RECONCILE_TIMER, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        // Shutdown the reconcile thread
        serverReconcileExecutor.shutdown();
        try {
            if (!serverReconcileExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                serverReconcileExecutor.shutdownNow();
            }
            System.out.println("SHUTDOWN: SR Executor shutdown gracefully.");
        } catch (InterruptedException e) {
            serverReconcileExecutor.shutdownNow();
            System.out.println("SHUTDOWN: SR Executor shutdown forcefully.");
        }
        System.exit(0);
    }
}
