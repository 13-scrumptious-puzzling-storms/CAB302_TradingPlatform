package TradingPlatform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkManager implements Runnable {
    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 2197;

    private static Socket socket;
    private static Boolean receive = false;

    @Override
    public void run() {

    }

    public static void SendRequest(String className, String methodName) throws IOException {
        transmit(new Request(className, methodName));
    }

    public static void SendRequest(String className, String methodName, String[] arguments) throws IOException {
        transmit(new Request(className, methodName, arguments));
    }

    public static Request GetResponse(String className, String methodName) throws IOException, ClassNotFoundException {
        receive = true;
        transmit(new Request(className, methodName, true));
        return Receive();
    }

    public static Request GetResponse(String className, String methodName, String[] arguments) throws IOException, ClassNotFoundException {
        receive = true;
        transmit(new Request(className, methodName, arguments, true));
        return Receive();
    }

    private static void transmit(Request request) throws IOException {
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
