package TradingPlatform;

import java.io.*;
import java.net.Socket;

public class ClientSend {
    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 2197;

    private static Socket socket;

    public ClientSend() throws IOException {
        System.out.println("Attempting Connection to: " + HOST_ADDRESS + ":" + PORT);
        socket = new Socket(HOST_ADDRESS, PORT);
    }

    public static void SendRequest(String className, String methodName) throws IOException {
        System.out.println("Constructing Request: " + className + " : " + methodName);
        transmit(new Request(className, methodName));
    }

    public static void SendRequest(String className, String methodName, String[] arguments) throws IOException {
        System.out.println("Constructing Request: " + className + " : " + methodName + " : " + arguments);
        transmit(new Request(className, methodName, arguments));
    }

    private static void transmit(Request request) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));) {
            objectOutputStream.writeObject(request);
        }
    }
}
