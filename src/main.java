import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;
import java.io.IOException;  // Import the IOException class to handle errors

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        //On défini le chemin des fichiers
        String indexPath = "src/site/index.html";
        String clientPath = "src/clients";
        String staffPath = "src/clients/staff.txt";

        try {
            //On vérifie si les fichiers existes
            isFile(indexPath);
            getClient(clientPath);
            List<String> clients = getClient2(staffPath);

            System.out.println(clients);

            PrintWriter writer = new PrintWriter("src/site/index.html");
            writer.println("""
                    <html>
                        <head>
                            <title>Welcome to Portail Agents!</title>
                        </head>
                        <body>
                            <h1>Portail des agents </h1>
                        </body>
                    </html>
                    """);
            writer.close();

            System.out.println("Fini");
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void isFile(String filePath) throws IOException {
        File file = new File(filePath);

        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        }
    }

    public static void getClient(String clientPath) throws IOException {
        File file = new File(clientPath);
        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                System.out.println(child.getName());
            }
        }
    }

    public static List<String> getClient2(String clientPath) throws IOException {
        List<String> clientList = new ArrayList<>();
        File clientFile = new File(clientPath);
        Scanner clientFileScan = new Scanner(clientFile);

       while(clientFileScan.hasNextLine()) {
           clientList.add(clientFileScan.nextLine());
       }
       clientFileScan.close();

        return clientList;
    }
}
