package TradingPlatform.NetworkProtocol;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.Request;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;

public class ServerSend implements Runnable {
    private static Socket socket;

    @Override
    public void run() {

    }

    public static void handleRequest(String className, String methodName, String[] arguments, Socket clientSocket) throws IOException {
        Connection connection = DBConnection.getInstance();
        System.out.println("Connection to database successful!");
        socket = clientSocket;
        switch (className) {
            case "OrganisationalUnitServer":
                switch (methodName) {
                    case "getName":
                        // need to get Server Send working
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(connection);
                        Transmit(new Request(className, methodName, new String[] {String.valueOf(DBInterface.getOrganisationalUnitName(Integer.parseInt(arguments[0])))}));
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

    public static void Transmit(Request request) throws IOException {
        System.out.println("Request required response. Sending response ...");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        }
        System.out.println("Connection closed.");
    }

}
