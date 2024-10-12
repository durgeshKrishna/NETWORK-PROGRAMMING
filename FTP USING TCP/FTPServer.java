import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    private static final int PORT = 5002;
    static final String SERVER_DIRECTORY = "C:/Users/durge/OneDrive/Desktop/FTP USING TCP";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                handleFileTransfer(socket);
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void handleFileTransfer(Socket socket) {
        try (
            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream())
        ) {
            String fileName = dataIn.readUTF();
            System.out.println("Client requested file: " + fileName);

            File file = new File(SERVER_DIRECTORY, fileName);
            if (file.exists() && !file.isDirectory()) {
                dataOut.writeUTF("File exists");
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        dataOut.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File " + fileName + " sent to client.");
                }
            } else {
                dataOut.writeUTF("File not found.");
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
