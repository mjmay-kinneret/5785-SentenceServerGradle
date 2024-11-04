package kinneret.sentenceServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {
    public static void main(String[] args) {

        // java -jar Client 127.0.0.1 5000

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

        System.out.printf("Connected to server %s:%d%n", serverIp, serverPort);

        // connect to server
        try (Socket connection = new Socket(serverAddress, serverPort);
             // create objects to read and write from the server
             PrintWriter pwOut = new PrintWriter(connection.getOutputStream());
             BufferedReader brIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             BufferedReader brKeyboard = new BufferedReader(new InputStreamReader(System.in))) {

            String line;
            do {
                System.out.print("Enter a sentence (blank to end): ");
                line = brKeyboard.readLine().trim();
                if (!line.isBlank() && !line.isEmpty()) {
                    pwOut.println(line);
                    pwOut.flush();

                    // read response
                    String response = brIn.readLine();
                    int length = Integer.parseInt(brIn.readLine());

                    System.out.println("Server response: " + response + " with length " + length);
                }
            } while (!line.isEmpty() && !line.isBlank());
            System.out.println("Goodbye!");
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}