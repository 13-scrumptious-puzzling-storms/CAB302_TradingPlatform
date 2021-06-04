package TradingPlatform.NetworkProtocol;

import TradingPlatform.Request;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A part of the Server side network protocol.
 * Waits for, and receives requests from Clients, rerouting
 * these requests to a separate thread managed by ServerSend.
 * Runs on a separate thread.
 */
public class ServerHandle implements Runnable {
    private static final int PORT = 2197;
    private static final int BACKLOG = 25;

    private static volatile Boolean stopFlag = false;

    private static ServerSend serverSendRunnable;
    private static Socket socket;

    /**
     * run() method required for a class implementing Runnable.
     * Sets the serverSendRunnable variable to the serverSendRunnable in ServerApp.
     * Starts the getRequests() method which will continuously run until the stopFlag is true.
     */
    @Override
    public void run() {
        try {
            serverSendRunnable = ServerApp.serverSendRunnable;
            getRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for connections on port PORT and can have up to
     * BACKLOG clients in queue. Will handle requests continuously until the
     * stopFlag is true. Requests received will be unpacked and sent to ServerSend.
     *
     * @throws IOException if Client's socket is invalid.
     * @throws ClassNotFoundException if fails to identify a Request object.
     */
    private static void getRequests() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);){
            System.out.println("Awaiting connections on port " + PORT + " ...");
            // Staying around for multiple connections
            while (!stopFlag) {
                socket = serverSocket.accept();
                System.out.println("\nIncoming connection from " + socket.getInetAddress() + ":" + socket.getPort());
                ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Request clientRequest = (Request) objectInputStream.readObject();
                System.out.println("Request received! Class: '" + clientRequest.getClassName() + "' Method: '" + clientRequest.getMethodName() + "' Arguments: '" + clientRequest.getArguments() + "'");
                if (clientRequest.getResponse() == false) {
                    System.out.println("Connection does not request a response. Closing socket.\nConnection Closed.");
                    objectInputStream.close();
                }
                // Forward unpacked request to ServerSend
                serverSendRunnable.handleRequest(clientRequest.getClassName(), clientRequest.getMethodName(), clientRequest.getArguments(), socket);
            }
        }
    }

    /**
     * Sets the stopFlag to true, ending the while loop in getRequests()
     */
    public static void end() { stopFlag = true; }
}
