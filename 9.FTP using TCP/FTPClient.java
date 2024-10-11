import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class FTPClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 5002;
    private static final String CLIENT_DIRECTORY = "D:/CSE-TCE/05SEM/22CS570/DK LAB/ftp using tcp"; // Client's file directory

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file name to request from server: ");
        String fileName = scanner.nextLine();

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            // Send the requested file name to the server
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(fileName);

            // Receive the response from the server
            InputStream input = socket.getInputStream();
            DataInputStream dataIn = new DataInputStream(input);

            // Check if the server reports "File not found."
            input.mark(4); // Mark the stream to allow for resetting if not a "File not found" message
            String serverMessage = dataIn.readUTF();

            if (serverMessage.equals("File not found.")) {
                // If file not found, print the error and do nothing further
                System.out.println("Server: " + serverMessage);
            } else {
                // Reset the input stream if the message wasn't "File not found."
                input.reset();

                // If the file exists, ask the user for the file name to save it as
                System.out.print("Enter the name to save the file as: ");
                String saveAs = scanner.nextLine();

                // File save path
                File saveFile = new File(CLIENT_DIRECTORY, saveAs);
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);

                // Read the file from the server and write it to the local file
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("File saved as \"" + saveAs + "\"");
                fileOutputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
