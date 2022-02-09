import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;
import java.io.IOException;  // Import the IOException class to handle errors

public class main {
    public static void main(String[] args) {
        //Liste des équipements
        Map<String, String> equipmentList = new HashMap<String, String>() {{
            put("mousqueton", "Mousqueton");
            put("gants", "Gants d’intervention");
            put("brassard", "Brassard de sécurité");
            put("menottes", "Porte menottes");
            put("cyno", "Bandeau agent cynophile");
            put("talky", "Talkies walkies");
            put("lampe", "Lampe Torche");
            put("kit", "Kit oreillette");
            put("taser", "Tasers");
            put("lacrymo", "Bombes lacrymogènes");
        }};

        //On défini le chemin des fichiers
        String indexPath = "src/site/index.html";
        String staffPath = "src/clients/staff.txt";

        try {
            isFile(indexPath, true);
            List<String> clients = getClients(staffPath);

            //Création du fichier index.html
            createIndex(indexPath, clients);

            for (String client : clients) {
                createClientPage(client, equipmentList);
            }
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createIndex(String indexPath, List<String> clients) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(indexPath);
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
    }

    //Création de la page client
    public static void createClientPage(String client, Map<String, String> equipmentList) throws IOException {
        isFile("src/site/" + client + ".html", true);
        String[] clientInfos = getClientFiles(client);// 0 : txt | 1 : image

        String clientInfoFile = getClientInfo(clientInfos[0]);

        String[] temp =  clientInfoFile.split(";"); // C'est moche mais j'en ai marre

        String[] description = Arrays.copyOfRange(temp, 0, 4);
        String[] personnalEquipment = Arrays.copyOfRange(temp, 5, temp.length);

    }

    //Valeur d'entrée : Le chemin voulus pour le fichier index.html
    public static boolean isFile(String filePath, boolean create) throws IOException {
        File file = new File(filePath);

        //Permet de savoir si le fichier index.html existe
        //Si il n'existe pas, on le crée
        if (!file.exists()) {
            if (!create) {
                return false;
            }
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        }

        return true;
    }

    //Valeur d'entrée : Le nom du client
    public static String[] getClientFiles(String client) throws IOException {
        File file = new File("src/clients/"+client);

        List<String> clientInfo = new ArrayList<>();
        //On récupère la list des fichiers
        File[] children = file.listFiles();
        String txtPath = null;
        String IdentityCard = null;

        if (children != null) {
            for (File child : children) {
                if (child.getAbsolutePath().substring(child.getAbsolutePath().length() - 3).equals("txt")) {
                    txtPath = child.getAbsolutePath();
                } else {
                    IdentityCard =child.getAbsolutePath();
                }
            }
        }

        String[] clientsInfo = new String[2];
        clientsInfo[0] = txtPath;
        clientsInfo[1] = IdentityCard;

        return clientsInfo;
    }

    //Valeur d'entrée : Le chemin vers le fichier staff.txt
    public static List<String> getClients(String clientPath) throws IOException {
        List<String> clientList = new ArrayList<>();
        File clientFile = new File(clientPath);
        Scanner clientFileScan = new Scanner(clientFile);

        //On lit le fichier tant qu'il y a un retour à la ligne
        while(clientFileScan.hasNextLine()) {
           clientList.add(clientFileScan.nextLine());
        }
        clientFileScan.close();

        return clientList;
    }
    public static String getClientInfo(String clientInfo) throws IOException {
        String clientInfoFile = "";

        //On lit le fichier tant qu'il y a un retour à la ligne
        if (isFile(clientInfo, false)) {
            File file = new File(clientInfo);
            Scanner infoFile = new Scanner(file);


            while (infoFile.hasNextLine()) {
                clientInfoFile += (clientInfoFile.equals("")) ?
                        infoFile.nextLine() :
                        ";" + infoFile.nextLine();
            }
            infoFile.close();
        }
        return clientInfoFile;
    }
}
