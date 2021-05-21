package TradingPlatform.NetworkProtocol;

import TradingPlatform.Request;

import java.io.*;
import java.net.Socket;

public class ServerSend implements Runnable {
    @Override
    public void run() {

    }

    public static void Transmit(Socket socket, Request request) throws IOException {
        System.out.println("Request required response. Sending response ...");
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        }
        System.out.println("Connection closed.");
    }

}
