package TradingPlatform;

import java.io.*;
import java.net.Socket;

public class ClientSend {
    private static final String HOST_ADDRESS = "127.0.0.1";
    private static final int PORT = 2197;

    private static Socket socket;
    private static ClientRequest clientRequest;

    public ClientSend() throws IOException {
        Socket socket = new Socket(HOST_ADDRESS, PORT);
    }

    public static void SendRequest(String className, String methodName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));) {
            objectOutputStream.writeObject(new ClientRequest(className, methodName));
        }
    }

    public static void SendRequest(String className, String methodName, String[] arguments) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));) {
            objectOutputStream.writeObject(new ClientRequest(className, methodName, arguments));
        }
    }
}
