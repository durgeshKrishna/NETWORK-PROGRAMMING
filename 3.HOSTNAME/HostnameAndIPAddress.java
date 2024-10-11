import java.net.*;
import java.net.*;

public class HostnameAndIPAddress {

    public static void main(String[] args) {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            String hostname = localhost.getHostName();
            String ipAddress = localhost.getHostAddress();

            System.out.println("Hostname: " + hostname);
            System.out.println("IP Address: " + ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
