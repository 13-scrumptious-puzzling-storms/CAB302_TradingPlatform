package TradingPlatform.NetworkProtocol;

import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalUnit;
import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;
import TradingPlatform.JDBCDataSources.JDBCUserDataSource;
import TradingPlatform.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
            case "JDBCOrganisationalAsset":
                switch (methodName) {
                    case "getOrganisationAssetsQuantity":
                        JDBCOrganisationalAsset DBInterface = new JDBCOrganisationalAsset(connection);
                        String[][] response = (DBInterface.getOrganisationAssetsQuantity(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response));
                        break;
                    case "getAssetType":
                        // ServerSend
                        break;
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
                    case "getUser": {
                        int userId = Integer.parseInt(arguments[0]);
                        JDBCUserDataSource userSource = new JDBCUserDataSource(userId, connection);
                        String[] response = new String[]{userSource.getUsername(),
                                Integer.toString(userSource.getAccountType().getValue()), Integer.toString(userSource.getOrganisationalUnit().getID())};
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "changePassword": {
                        int userId = Integer.parseInt(arguments[0]);
                        JDBCUserDataSource userSource = new JDBCUserDataSource(userId, connection);
                        String[] response = new String[]{Boolean.toString(userSource.ChangePassword(arguments[1], arguments[2]))};
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
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
