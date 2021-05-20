package TradingPlatform.NetworkProtocol;

import TradingPlatform.OrganisationalUnit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHandle {
    private static final int PORT = 2197;
    private static final int BACKLOG = 25;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        testRead();

        try (ServerSocket serverSocket = new ServerSocket(PORT, BACKLOG);){
            // Staying around for multiple connections
            for (;;) {
                Socket socket = serverSocket.accept();
                try(ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));) {
                    ServerRequest clientRequest = (ServerRequest) objectInputStream.readObject();
                    handleRequest(clientRequest.getClassName(), clientRequest.getMethodName(), clientRequest.getArguments());
                }
            }
        }
    }

    private static void handleRequest(String className, String methodName, String[] arguments) {
        switch (className) {
            case "OrganisationalUnit":
                switch (methodName) {
                    case "getName":
                        // need to get Server Send working
                        //ServerSend(OrganisationalUnitServer.getName(Integer.parseInt(arguments[0])));
                        //ServerSend ...;
                        break;
                    case "getCredits":
                        //ServerSend ...;
                        break;
                    default:
                        System.out.println("Invalid Method");
                }
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
                }
            default:
                System.out.println("Invalid Class");
        }
    }

    private static void testRead() throws FileNotFoundException {
        //File filename = new File("mySerialData");

        //try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));) {
        //    OrganisationalUnitServer serialData = (OrganisationalUnitServer) objectInputStream.readObject();
        //    System.out.println("serialData's ID: " + serialData.getID());
        //    System.out.println("serialData's Credits: " + serialData.getCredits());
        //}
    }
}
