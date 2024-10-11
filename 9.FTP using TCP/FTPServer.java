import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer{
    private static final int PORT = 5002;
    static final String SERVER_DIRECTORY = "D:/CSE-TCE/05SEM/22CS570/DK LAB/ftp using tcp"; // Server's file directory

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new FileTransferHandler(socket).start(); // Handle each client in a new thread
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class FileTransferHandler extends Thread {
    private Socket socket;

    public FileTransferHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(output)
        ) {
            // Read file name requested by the client
            String fileName = reader.readLine();
            System.out.println("Client requested file: " + fileName);

            // Create file path
            File file = new File(FTPServer.SERVER_DIRECTORY, fileName);
            if (file.exists() && !file.isDirectory()) {
                // Send file to client
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dataOut.write(buffer, 0, bytesRead);
                }

                fileInputStream.close();
                System.out.println("File " + fileName + " sent to client.");
            } else {
                // If file doesn't exist, send error message
                dataOut.writeUTF("File not found.");
            }
            
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
