import java.io.*;
import java.net.*;
import java.util.Scanner;

class DnsServer1{
    public static void main(String[] args) throws Exception {
        Process process = Runtime.getRuntime().exec("nslookup");
        Scanner scanner = new Scanner(process.getInputStream());
        String ipAddress = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("Address")) {
                ipAddress = line.split(":")[1].trim();
                System.out.println("DNS Server Address: " + ipAddress);
                break;
            }
        }
        if (ipAddress != null) {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostname = inetAddress.getHostName();
            System.out.println("Hostname: " + hostname);
        }
        String hostNameToResolve = "www.amazon.com";
        InetAddress[] addresses = InetAddress.getAllByName(hostNameToResolve);
        System.out.println("IP addresses for " + hostNameToResolve + ":");
        try {
            for (InetAddress address : addresses) {
                System.out.println(address.getHostAddress());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            scanner.close();
        }
    }
}
