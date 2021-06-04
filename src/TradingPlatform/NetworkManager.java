package TradingPlatform;

import java.io.*;
import java.net.Socket;

/**
 * Manages Client side of the network protocol.
 * Responsible for sending and receiving messages from the Sever.
 */
public class NetworkManager implements Runnable {
    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 2197;

    private static Socket socket;
    private static Boolean receive = false;

    @Override
    public void run() {

    }

    /**
     * Prepares a Request to send to the server.
     * The Request will inform the server of which method of which
     * class the Client would like the Server to execute.
     * No reply from the Server is expected.
     *
     * @param className The name of the class to access.
     * @param methodName The method within the class to execute.
     * @throws IOException if Server's socket is invalid.
     */
    public static void SendRequest(String className, String methodName) throws IOException {
        transmit(new Request(className, methodName));
    }

    /**
     * Prepares a Request to send to the server.
     * This Request will consist of the Class to access,
     * the Method to invoke and the values of the arguments
     * required for the Method.
     * No reply from the Server is expected.
     *
     * @param className The name of the class to access.
     * @param methodName The method within the class to execute.
     * @param arguments The values to use as the arguments for the method.
     * @throws IOException if Server's socket is invalid.
     */
    public static void SendRequest(String className, String methodName, String[] arguments) throws IOException {
        transmit(new Request(className, methodName, arguments));
    }

    /**
     * Prepares a Request to send to the server.
     * This Request will consist of the Class to access and
     * the Method to invoke.
     * The Client will require a response from the Server.
     *
     * @param className The name of the class to access.
     * @param methodName The method within the class to execute.
     * @return Returns the response from the Server.
     * @throws IOException if Server's socket is invalid.
     * @throws ClassNotFoundException if Server's response is not a valid Request object.
     */
    public static Request GetResponse(String className, String methodName) throws IOException, ClassNotFoundException {
        receive = true;
        transmit(new Request(className, methodName, true));
        return Receive();
    }

    /**
     * Prepares a Request to send to the server.
     * This Request will consist of the Class to access,
     * the Method to invoke and the values of the arguments
     * required for the Method.
     * The Client will require a response from the Server.
     *
     * @param className The name of the class to access.
     * @param methodName The method within the class to execute.
     * @param arguments The values to use as the arguments for the method.
     * @return Returns the response from the Server.
     * @throws IOException if Server's socket is invalid.
     * @throws ClassNotFoundException if Server's response is not a valid Request object.
     */
    public static Request GetResponse(String className, String methodName, String[] arguments) throws IOException, ClassNotFoundException {
        receive = true;
        transmit(new Request(className, methodName, arguments, true));
        return Receive();
    }

    /**
     * Attempts to send the prepared Request to the Server.
     *
     * @param request The prepared Request.
     */
    private static void transmit(Request request) {
        try {
            socket = new Socket(HOST_ADDRESS, PORT);
            System.out.println("Successfully connected to server!");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            objectOutputStream.writeObject(request);
            System.out.println("Request sent. Class: '" + request.getClassName() + "' Method: '" + request.getMethodName() + "' Arguments: '" + request.getArguments() + "'");
            objectOutputStream.flush();
            if (!receive) {
                objectOutputStream.close();
                System.out.println("Connection closed.\n");
            }
        }
        catch (Exception ex) {
            ClientApp.displayError("Failed to connect to server. \nThe server appears to be offline.\n");
        }
    }

    /**
     * After a Request is sent to the Server, using the same socket,
     * this method will wait for a response from the Server.
     *
     * @return Attempts to return the response from the Server
     * @throws IOException if Server's socket is invalid.
     * @throws ClassNotFoundException if Server's response is not a valid Request object.
     */
    private static Request Receive() throws IOException, ClassNotFoundException {
        receive = false;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
            Request response = (Request) objectInputStream.readObject();
            System.out.println("Response received! Class: '" + response.getClassName() + "' Method: '" + response.getMethodName() + "' Arguments: '" + response.getArguments() + "'");
            System.out.println("Connection closed.\n");
            return response;
        }
    }
}
