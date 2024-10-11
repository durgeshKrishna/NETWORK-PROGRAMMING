import java.io.*;
import java.net.*;

public class FileServerUDP {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(5000);
            System.out.println("Server is listening on port 5000...");

            
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);  // Receive file name from client
            String requestedFileName = new String(packet.getData(), 0, packet.getLength()).trim();

            InetAddress clientAddress = packet.getAddress();
            int clientPort = packet.getPort();

            
            File file = new File(requestedFileName);
            byte[] response = new byte[1024];

            if (file.exists() && !file.isDirectory()) {
                
                response = "File found".getBytes();
                DatagramPacket confirmationPacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
                socket.send(confirmationPacket);

                
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);

                byte[] fileBuffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bis.read(fileBuffer)) != -1) {
                    DatagramPacket filePacket = new DatagramPacket(fileBuffer, bytesRead, clientAddress, clientPort);
                    socket.send(filePacket);
                }

               
                socket.send(new DatagramPacket(new byte[0], 0, clientAddress, clientPort));

                bis.close();
                System.out.println("File sent successfully from server.");
            } else {
                
                response = "File not found".getBytes();
                DatagramPacket errorPacket = new DatagramPacket(response, response.length, clientAddress, clientPort);
                socket.send(errorPacket);
                System.out.println("File not found on server.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
