package kinneret.sentenceServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HandleClient extends Thread {
    Socket clientSocket;

    public HandleClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (
                PrintWriter pwOut = new PrintWriter(clientSocket.getOutputStream());
                BufferedReader brIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            String inputLine;
            while (true) {
                inputLine = brIn.readLine();

                if (inputLine == null || inputLine.trim().isBlank() || inputLine.trim().isEmpty()) {
                    break;
                }

                String outputLine = inputLine.toUpperCase();
                int length = outputLine.length();

                pwOut.println(outputLine);
                pwOut.println(length);
                pwOut.flush();
            }
        } catch (IOException e) {
            System.out.println("Error communicating with client: " + e.getMessage());
        }
    }
}