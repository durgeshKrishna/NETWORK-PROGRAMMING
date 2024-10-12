import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 5002;
    private static final String CLIENT_DIRECTORY = "C:/Users/durge/OneDrive/Desktop/FTP USING TCP";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file name to request from server: ");
        String fileName = scanner.nextLine();

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
            dataOut.writeUTF(fileName);

            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            String serverMessage = dataIn.readUTF();

            if (serverMessage.equals("File not found.")) {
                System.out.println("Server: " + serverMessage);
            } else {
                System.out.print("Enter the name to save the file as: ");
                String saveAs = scanner.nextLine();

                File saveFile = new File(CLIENT_DIRECTORY, saveAs);
                try (FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = dataIn.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("File saved as \"" + saveAs + "\"");
                }
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
