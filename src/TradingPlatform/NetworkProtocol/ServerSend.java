package TradingPlatform.NetworkProtocol;

import TradingPlatform.AccountType;
import TradingPlatform.AssetType;
import TradingPlatform.JDBCDataSources.*;
import TradingPlatform.OrganisationalUnit;
import TradingPlatform.Request;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.Map;

public class ServerSend implements Runnable {
    private static Socket socket;

    @Override
    public void run() {

    }

    public static void handleRequest(String className, String methodName, String[] arguments, Socket clientSocket) throws IOException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance();
        System.out.println("Connection to database successful!");
        socket = clientSocket;
        switch (className) {
            case "OrganisationalUnitServer":
                switch (methodName) {
                    case "getName": {
                        // need to get Server Send working
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(connection);
                        Transmit(new Request(className, methodName, new String[]{String.valueOf(DBInterface.getOrganisationalUnitName(Integer.parseInt(arguments[0])))}));
                        //ServerSend(OrganisationalUnitServer.getName(Integer.parseInt(arguments[0])));
                        //ServerSend ...;
                        break;
                    }
                    case "getCredits": {
                        var DBInterface = new JDBCOrganisationalUnit(connection);
                        Transmit(new Request(className, methodName, new String[]{String.valueOf(DBInterface.getOrganisationalUnitCredits(Integer.parseInt(arguments[0])))}));
                        break;
                    }
                    case "setCredits": {
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(connection);
                        int orgUnitId = Integer.parseInt(arguments[0]);
                        int newCredits = Integer.parseInt(arguments[1]);
                        DBInterface.UpdateOrganisationalUnitCredits(orgUnitId, newCredits);
                        break;
                    }
                    case "getOrganisationalUnit": {
                        var DBInterface = new JDBCOrganisationalUnit(connection);
                        OrganisationalUnit orgUnit = DBInterface.getOrganisationalUnit(Integer.parseInt(arguments[0]));
                        String[] orgUnitDetails = new String[] {
                                orgUnit.getName(Integer.parseInt(arguments[0])),
                                Integer.toString(orgUnit.getCredits(Integer.parseInt(arguments[0])))
                        };
                        Transmit(new Request(className, methodName, orgUnitDetails));
                        break;
                    }
                    case "getAllOrgs": {
                        var DBInterface = new JDBCOrganisationalUnit(connection);
                        String[][] allOrgUnits = DBInterface.getAllOrganisationalUnits();
                        Transmit(new Request(className, methodName, allOrgUnits));
                        break;
                    }
                    case "addOrgUnit": {
                        var DBInterface = new JDBCOrganisationalUnit(connection);
                        int newOrgId = DBInterface.addOrganisationalUnit(arguments[0], Integer.parseInt(arguments[1]));
                        Transmit(new Request(className, methodName, new String[]{ Integer.toString(newOrgId) }));
                        break;
                    }
                    case "UpdateOrganisationalUnitCredits": {
                        JDBCOrganisationalUnit DBInterface = new JDBCOrganisationalUnit(connection);
                        Boolean successful = DBInterface.UpdateOrganisationalUnitCredits(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                        Transmit(new Request(className, methodName, new String[]{ Boolean.toString(successful) }));
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCOrganisationalAsset":
                switch (methodName) {
                    case "addOrganisationAsset": {
                        JDBCOrganisationalAsset DBInterface = new JDBCOrganisationalAsset(connection);
                        DBInterface.addOrganisationAsset(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                        break;
                    }
                    case "getOrganisationAssetsQuantity": {
                        JDBCOrganisationalAsset DBInterface = new JDBCOrganisationalAsset(connection);
                        String[][] response = (DBInterface.getOrganisationAssetsQuantity(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "updateOrganisationAssetsQuantity": {
                        JDBCOrganisationalAsset DBInterface = new JDBCOrganisationalAsset(connection);
                        DBInterface.UpdateOrganisationAssetQuantity(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                        break;
                    }
                    case "getAssetType": {
                        // ServerSend
                        break;
                    }
                    case "getOrganisationAssetId": {
                        var DBInterface = new JDBCOrganisationalAsset(connection);
                        int orgAssetId = DBInterface.getOrganisationAssetId(Integer.parseInt(arguments[0]), Integer.parseInt(arguments[1]));
                        Transmit(new Request(className, methodName, new String[]{Integer.toString(orgAssetId)}));
                        break;
                    }
                    case "getOrganisationAssetQuantity": {
                        var DBInterface = new JDBCOrganisationalAsset(connection);
                        int orgAssetQuantity = DBInterface.getOrganisationAssetQuantity(Integer.parseInt(arguments[0]));
                        Transmit(new Request(className, methodName, new String[]{Integer.toString(orgAssetQuantity)}));
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCTradeDataSource":
                switch(methodName){
                    case "getSellOrders": {
                        var DBInterface = new JDBCTradeDataSource(connection);
                        String[][] response = (DBInterface.getSellOrders(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "getBuyOrders": {
                        var DBInterface1 = new JDBCTradeDataSource(connection);
                        String[][] response1 = (DBInterface1.getBuyOrders(Integer.parseInt(arguments[0])));
                        Transmit(new Request(className, methodName, response1));
                        break;
                    }
                    case "setCancel": {
                        var DBInterface2 = new JDBCTradeDataSource(connection);
                        Boolean completed = DBInterface2.setCancel(Integer.parseInt(arguments[0]));
                        Transmit(new Request(className, methodName, new String[]{Boolean.toString(completed)}));
                        break;
                    }
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
                        boolean success = JDBCUserDataSource.addUser(username, password, accountType, OrgUnitId, connection);
                        Transmit(new Request(className, methodName, new String[]{ Boolean.toString(success)}));
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
                        String username = arguments[0];
                        String password = arguments[1];
                        boolean success = JDBCUserDataSource.adminChangeUserPassword(username, password, connection);
                        Transmit(new Request(className, methodName, new String[]{ Boolean.toString(success) }));
                        break;
                    }
                    default:
                        System.out.println("Invalid Method");
                        break;
                }
                break;
            case "JDBCAssetType":
                switch (methodName){
                    case "getAllAssetNames": {
                        JDBCAssetType DBInterface = new JDBCAssetType(connection);
                        String[] response = (DBInterface.getAllAssetNames());
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "getAllAssets": {
                        JDBCAssetType DBInterface = new JDBCAssetType(connection);
                        String[][] response = (DBInterface.getAllAssetTypes());
                        Transmit(new Request(className, methodName, response));
                        break;
                    }
                    case "getAssetId": {
                        JDBCAssetType DBInterface = new JDBCAssetType(connection);
                        int id = (DBInterface.getAssetId(arguments[0]));
                        Transmit(new Request(className, methodName, new String[]{Integer.toString(id)}));
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
            case "JDBCTradeReconcileSource":
                switch (methodName){
                    case "getMostRecentAssetTypeTradeDetails": {
                        JDBCTradeReconcileSource DBInterface = new JDBCTradeReconcileSource(connection);
                        String[][] response = (DBInterface.getMostRecentAssetTypeTradeDetails());
                        Transmit(new Request(className, methodName, response));
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

    public static void Transmit(Request request) throws IOException {
        System.out.println("Request required response. Sending response ...");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        }
        System.out.println("Connection closed.");
    }

}
