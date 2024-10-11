import java.io.*;
import java.net.*;

public class serverecho{
    public static void main(String[] args) {
        try (ServerSocket ssoc = new ServerSocket(3110);
             Socket soc = ssoc.accept();
             DataInputStream dis = new DataInputStream(soc.getInputStream());
             DataOutputStream dos = new DataOutputStream(soc.getOutputStream())
        ) {
            String rec_msg;
            
            while ((rec_msg = dis.readUTF()) != null) {
                if (rec_msg.equals("end")) {
                    System.out.println("Client disconnected");
                    break;
                } else {
                    dos.writeUTF(rec_msg);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
