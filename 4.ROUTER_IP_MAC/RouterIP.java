import java.io.IOException;
import java.util.Scanner;

class RouterIP {
    public static void main(String[] args) {
        String ipAddress = "";
        try {
            Process process = Runtime.getRuntime().exec("ipconfig");
            Scanner reader = new Scanner(process.getInputStream());

            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                if (line.trim().startsWith("Default Gateway")) {
                    ipAddress = line.substring(line.indexOf(":") + 1).trim();
                    System.out.println("Router IP Address: " + ipAddress);
                    break;
                }
            }

            process = Runtime.getRuntime().exec("arp -a");
            reader = new Scanner(process.getInputStream());

            boolean macFound = false;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                if (line.trim().startsWith(ipAddress)) {
                    String macAddress = line.substring(line.indexOf(ipAddress) + 11).trim();
                    macAddress = macAddress.substring(0, 17);
                    System.out.println("Router MAC Address: " + macAddress);
                    macFound = true;
                    break;
                }
            }
            if (!macFound) {
                System.out.println("MAC Address not found for IP Address: " + ipAddress);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
