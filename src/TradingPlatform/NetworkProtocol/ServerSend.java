package TradingPlatform.NetworkProtocol;

import TradingPlatform.AccountType;
import TradingPlatform.JDBCDataSources.*;
import TradingPlatform.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;

/**
 * A part of the Server side network protocol.
 * Handles the requests received by ServerHandle and sends
 * back a reply to the Client if the request requires so.
 * Runs on a separate thread.
 */
public class ServerSend implements Runnable {
    private static Socket socket;
    public static volatile Boolean isWorking = false;

    @Override
    public void run() {

    }

    /**
     * Will execute the requests received from Clients.
     * It will invoke a server-side method as requested by the Client.
     * The class to access is className, the method of the class to
     * invoke is methodName, the arguments of the method (if any)
     * is an array of Strings arguments. If the Clients requires a
     * response, a Request will be prepared and the reply will be sent
     * using the Client's socket clientSocket. ServerSend also indicates
     * with Boolean isWorking, whether or not it is busy handling a request.
     *
     * @param className the class the server will access.
     * @param methodName the method of the class to invoke.
     * @param arguments the arguments of the method (if any).
     * @param clientSocket the Client's socket.
     * @throws IOException if Client's socket is invalid.
     */
    public static void handleRequest(String className, String methodName, String[] arguments, Socket clientSocket) throws IOException {
        isWorking = true;
        Connection connection = DBConnection.getInstance();
        System.out.println("Connection to database successful!");
        socket = clientSocket;
        switch (className) {
            case "OrganisationalUnitServer":
                switch (methodName) {
                    case "getName": {
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(connection);
                        Transmit(new Request(className, methodName, new String[]{String.valueOf(DBInterface.getOrganisationalUnitName(Integer.parseInt(arguments[0])))}));
                        break;
                    }
                    case "getCredits": {
                        // ...
                        break;
                    }
                    case "getAllOrgs": {
                        var DBInterface = new JDBCOrganisationalUnit(connection);
                        String[][] allOrgUnits = DBInterface.getAllOrganisationalUnits();
                        Transmit(new Request(className, methodName, allOrgUnits));
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCOrganisationalAsset":
                switch (methodName) {
                    case "getOrganisationAssetsQuantity": {
                        JDBCOrganisationalAsset DBInterface = new JDBCOrganisationalAsset(connection);
                        String[][] response = (DBInterface.getOrganisationAssetsQuantity(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "getAssetType": {
                        // ...
                        break;
                    }
                    case "addAssetType": {
                        var DBInterface = new JDBCAssetType(connection);
                        DBInterface.addAssetType(arguments[0]);
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCTradeDataSource":
                switch(methodName){
                    case "getSellOrders":
                        JDBCTradeDataSource DBInterface = new JDBCTradeDataSource(connection);
                        String[][] response = (DBInterface.getSellOrders(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response));
                        break;
                    case "getBuyOrders":
                        JDBCTradeDataSource DBInterface1 = new JDBCTradeDataSource(connection);
                        String[][] response1 = (DBInterface1.getBuyOrders(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response1));
                        break;
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCUserDataSource":
                switch (methodName){
                    case "getUserId":{
                        String username = arguments[0];
                        String hashPass = arguments[1];
                        int userID = JDBCUserDataSource.getUserId(username, hashPass, connection);
                        Transmit(new Request(className, methodName, new String[] { Integer.toString(userID) } ));
                        break;
                    }
                    case "getUser": {
                        int userId = Integer.parseInt(arguments[0]);
                        JDBCUserDataSource userSource = new JDBCUserDataSource(userId, connection);
                        String[] response = new String[]{userSource.getUsername(),
                                Integer.toString(userSource.getAccountType().getValue()), Integer.toString(userSource.getOrganisationalUnit().getID())};
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "addUser": {
                        String username = arguments[0];
                        String password = arguments[1];
                        AccountType accountType = AccountType.valueOf(arguments[2]);
                        int OrgUnitId = Integer.parseInt(arguments[3]);
                        JDBCUserDataSource.addUser(username, password, accountType, OrgUnitId, connection);
                        break;
                    }
                    case "changePassword": {
                        int userId = Integer.parseInt(arguments[0]);
                        JDBCUserDataSource userSource = new JDBCUserDataSource(userId, connection);
                        String[] response = new String[]{Boolean.toString(userSource.ChangePassword(arguments[1], arguments[2]))};
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "adminChangeUserPassword": {
                        int userId = Integer.parseInt(arguments[0]);
                        String password = arguments[1];
                        JDBCUserDataSource.adminChangeUserPassword(userId, password, connection);
                        break;
                    }
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

    /**
     * Attempts to reply to a Client with the prepared Request from
     * handleRequest() (if there is one).
     *
     * @param request the prepared reply to send to the client.
     * @throws IOException if Client's socket is invalid.
     */
    public static void Transmit(Request request) throws IOException {
        System.out.println("Request required response. Sending response ...");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        }
        System.out.println("Connection closed.");
        isWorking = false;
    }
}
