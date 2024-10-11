import java.io.*;
import java.net.*;
class DnsServer {
    public static void main(String[] args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("nslookup");
        Process process = pb.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String ipAddress = null;
        while ((line = br.readLine()) != null) {
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
        }
    }
}