import java.net.Socket;
import java.util.*;
public class PortScanner1{
    public static void main(String[] args) {
        String host = "localhost"; 
        int startPort = 1; 
        int endPort = 65535; 
        // Scanner sc=new Scanner(System.in);
        // System.out.println("Enter the startPort:");
        // //int startPort=sc.nextInt();
        // System.out.println("Enter the endPort:");
        // int endPort=sc.nextInt();
        System.out.println("Scanning ports on " + host + "...");
        for (int port = startPort; port <= endPort; port++) {
            try {
                Socket socket = new Socket(host, port);
                socket.close();
                System.out.println("Port " + port + " is open");
            } catch (Exception ex) {
               
            }
        }
    }
}
