package TradingPlatform.NetworkProtocol;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class ServerHandle implements Runnable {
    private static final int PORT = 2197;
    private static final int BACKLOG = 25;

    private static volatile Boolean stopFlag = false;

    // Should this be here? Idk should it be static? Idk
    private static ServerSend serverSendRunnable;
    private static Socket socket;

    // WHY DOES THIS WORK? I dont pass in the ServerApp I'm talking about. Does this go and make another ServerApp to create its own server runnable?
    // hmm maybe not because im not saying new ServerApp simply referring to ServerApp
    @Override
    public void run() {
        try {
            serverSendRunnable = ServerApp.serverSendRunnable;
            getRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                serverSendRunnable.handleRequest(clientRequest.getClassName(), clientRequest.getMethodName(), clientRequest.getArguments(), socket);
            }
        }
    }

    public static void end() {
        stopFlag = true;
    }

    private static void testRead() throws IOException {
        File filename = new File("mySerialData");

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));) {
            OrganisationalUnitServer serialData = (OrganisationalUnitServer) objectInputStream.readObject();
            System.out.println("serialData's ID: " + serialData.getID());
            System.out.println("serialData's Credits: " + serialData.getCredits());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
