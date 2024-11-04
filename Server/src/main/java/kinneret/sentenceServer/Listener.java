package kinneret.sentenceServer;

import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {

    ServerSocket serverSocket;


    public Listener(ServerSocket socket) {
        this.serverSocket = socket;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted() && !serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                HandleClient handleClient = new HandleClient(clientSocket);
                handleClient.start();
            }
        } catch (Exception e) {
            // something went wrong!
        }
    }
}
