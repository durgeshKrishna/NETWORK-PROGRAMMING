import java.net.*;
import java.io.*;
import java.util.Date;

class Server{
    public static void main(String[] args) throws Exception {
        // Start server on port 8081
        ServerSocket ss = new ServerSocket(8081);
        System.out.println("Time Server Started on Port 8081...");
        System.out.println("Waiting for Client...");

        // Accept client connection
        Socket s = ss.accept();
        System.out.println("Client " + s + " Connected to Time Server...");

        // Initialize streams for communication
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String clientMessage = "", serverMessage = "";

        // Communication loop
        while (!clientMessage.equals("stop")) {
            // Receive message from client
            clientMessage = din.readUTF();
            System.out.println("Client says: " + clientMessage);

            // Get the server's custom message
            System.out.print("Enter message to send to client: ");
            serverMessage = br.readLine(); // Read server input

            // Append the current time and date to the server's message
            String fullResponse = serverMessage + " [Server Response at: " + new Date() + "]";
            dout.writeUTF(fullResponse);  // Send the message to the client
            dout.flush();
        }

        // Close connections
        din.close();
        s.close();
        ss.close();
    }
}
