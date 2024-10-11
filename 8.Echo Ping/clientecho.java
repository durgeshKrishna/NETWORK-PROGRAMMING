import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clientecho{
    public static void main(String[] args) {
        try (
            Socket soc = new Socket("localhost", 3110);
            DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
            DataInputStream dis = new DataInputStream(soc.getInputStream());
            Scanner n = new Scanner(System.in)
        ) {
            System.out.println("Start echoing");
            String sent_msg;
            
            while (true) {
                sent_msg = n.nextLine();
                dos.writeUTF(sent_msg);
                
                if (sent_msg.equals("end")) {
                    System.out.println("Disconnected");
                    break;
                } else {
                    System.out.println("Echoed from server: " + dis.readUTF());
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
