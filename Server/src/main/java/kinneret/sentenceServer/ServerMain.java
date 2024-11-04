package kinneret.sentenceServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        // java -jar Server 127.0.0.1 5000

        // retrieve parameters from the CMD
        String serverIp = args[0];

        int serverPort;
        try {
            serverPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("Error parsing port number: " + nfe.getMessage());
            return;
        }
        // retrieved server IP address
        InetAddress serverAddress = null;
        try {
            serverAddress = InetAddress.getByName(serverIp);
        } catch (UnknownHostException e) {
            System.out.println("Error parsing IP address: " + e.getMessage());
            return;
        }

        // create a server socket to listen
        ServerSocket serverSocket = new ServerSocket(serverPort, 50, serverAddress);
        // pass the server socket to the listening thread
        Listener listener = new Listener(serverSocket);
        listener.start();

        System.out.println("Listening for incoming connections.");
        System.out.println("Enter q to quit");

        BufferedReader brKeyboard = new BufferedReader(new InputStreamReader(System.in));
        String line;
        do {
            try {
                line = brKeyboard.readLine();
            } catch (IOException e) {
                break;
            }
        } while (!line.equalsIgnoreCase("q"));

        listener.interrupt();
        serverSocket.close();

        System.out.println("Stopped listening.  Goodbye!");
    }
}
