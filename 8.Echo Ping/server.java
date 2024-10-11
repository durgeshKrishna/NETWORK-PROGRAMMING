import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        int port = 12345;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {
                    System.out.println("Client connected.");
                    out.writeUTF("Ping from server"); // Send message using DataOutputStream
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }
}
