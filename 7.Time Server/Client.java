import java.net.*;
import java.io.*;
import java.util.Date;

class Client{
    public static void main(String[] args) throws Exception {
        // Connect to the server on localhost and port 8081
        Socket s = new Socket("localhost", 8081);
        System.out.println("Client Started on Port " + s.getLocalPort());
        System.out.println("Connected to Time Server " + s.getInetAddress() + ":" + s.getPort());

        // Initialize streams for communication
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String clientMessage = "", serverMessage = "";

        // Communication loop
        while (!clientMessage.equals("stop")) {
            // Client sends a message
            System.out.print("Enter message: ");
            clientMessage = br.readLine();
            dout.writeUTF(clientMessage);
            dout.flush();

            // Receive and print the server's response
            serverMessage = din.readUTF();
            System.out.println("Server says: " + serverMessage);
        }

        // Close connections
        dout.close();
        din.close();
        s.close();
    }
}
