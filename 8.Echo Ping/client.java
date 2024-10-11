import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        String host = "127.0.0.1"; 
        int port = 12345;

        try (Socket socket = new Socket(host, port);
             DataInputStream in = new DataInputStream(socket.getInputStream())) {
            System.out.println("Server is reachable.");
            String response = in.readUTF(); // Read message using DataInputStream
            System.out.println("Received: " + response);
        } catch (Exception e) {
            System.err.println("Server not reachable: " + e.getMessage());
        }
    }
}
