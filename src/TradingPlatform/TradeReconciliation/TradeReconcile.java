package TradingPlatform.TradeReconciliation;

import TradingPlatform.NetworkProtocol.DBConnection;

import java.sql.Connection;

public class TradeReconcile implements Runnable {

    private static final int SLEEP_TIME = 1000; // Wakes up every 10 seconds
    private static Connection connection;
    private static Object tradeLock;


    public TradeReconcile(Object tradeLock){
        TradeReconcile.tradeLock = tradeLock;
        try {
            connection = DBConnection.getInstance();
            System.out.println("Connection to database successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){


            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                if (Thread.interrupted()){
                    return;
                }
            }
        }
    }

    /**
     * This method reconciles all current outstanding trades
     */
    private void ReconcileCurrentTrades(){
        // Need to have the trade lock to synchronise trades
        synchronized (tradeLock){

        }
    }
}
