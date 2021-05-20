package TradingPlatform.NetworkProtocol;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.Request;

import java.io.*;
import java.lang.invoke.VarHandle;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class ServerHandle extends Thread {
    private static final int PORT = 2197;
    private static final int BACKLOG = 25;

    private static Connection connection;

    @Override
    public void run() {
        try {
            connection = DBConnection.getInstance();
            getRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getRequests() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);){
            // Staying around for multiple connections
            for (;;) {
                Socket socket = serverSocket.accept();
                try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));) {
                    Object serialData = objectInputStream.readObject();
                    Request clientRequest = (Request) serialData;
                    System.out.println("serialData's ID: " + clientRequest.getClassName());
                    System.out.println("serialData's Credits: " + clientRequest.getMethodName());
                    System.out.println("serialData's Credits: " + clientRequest.getArguments()[0]);
                    handleRequest(clientRequest.getClassName(), clientRequest.getMethodName(), clientRequest.getArguments());
                }
            }
        }
    }

    private static void handleRequest(String className, String methodName, String[] arguments) {
        System.out.println("Got request: " + className + " " + methodName + " " + arguments);
        switch (className) {
            case "OrganisationalUnitServer":
                switch (methodName) {
                    case "getName":
                        // need to get Server Send working
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(Integer.parseInt(arguments[0]), connection);
                        System.out.println(DBInterface.getOrganisationalUnitName());
                        //ServerSend(OrganisationalUnitServer.getName(Integer.parseInt(arguments[0])));
                        //ServerSend ...;
                        break;
                    case "getCredits":
                        //ServerSend ...;
                        break;
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "OrganisationAsset":
                switch (methodName) {
                    case "getQuantity":
                        // ServerSend
                        break;
                    case "getAssetType":
                        // ServerSend
                        break;
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            default:
                System.out.println("Invalid Class");
                break;
        }
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
