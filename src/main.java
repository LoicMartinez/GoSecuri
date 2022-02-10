import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
        String staffPath = "src/agents/staff.txt";

        try {
            isFile(indexPath, true);
            List<String> agents = getAgents(staffPath);

            //Création du fichier index.html
            createIndex(indexPath, agents);

            //Création de la page agent pour chacun des agents
            for (String agent : agents) {
                createAgentPage(agent, equipmentList);

            //Création du fichier css
            createCSS();
            }
        } catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createIndex(String indexPath, List<String> agents) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(indexPath);

        String headerHTML = """
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <link rel="stylesheet" href="Style.css">
                    <title></title>
                </head>
                """;

        String identityHTML = """
                <body>
                    <div>
                        <div id="header">
                            <img src="Assets/Images/GoSecuri.png" alt="GO Securi" id="logo">
                            <a href="">nav item 1</a>
                            <a href="">nav item 2</a>
                        </div>
                        <div id="infoAgent">
                            <div id="itemList">
                               <p>Liste des utilisateurs :</p>
                               <ul>
                """;

        String agentList = "";

        for (String agent: agents) {
            agentList += String.format("""
                    <li>
                    <a href="%s.html"> %s </a>
                    </li>
                    """, agent, agent);
        }

        String endHTML = """
                                </ul>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """;

        //On crée le fichier avec les String précédement créé
        writer.println(headerHTML + identityHTML + agentList + endHTML);
        writer.close();

        System.out.println("Fini");
    }

    //Création de la page agent
    public static void createAgentPage(String agent, Map<String, String> equipmentList) throws IOException {
        isFile("src/site/" + agent + ".html", true);
        String[] agentInfos = getAgentFiles(agent);// 0 : txt | 1 : image

        String agentInfoFile = getAgentInfo(agentInfos[0]);

        String[] temp =  agentInfoFile.split(";"); // C'est moche mais j'en ai marre

        String[] description = Arrays.copyOfRange(temp, 0, 4);
        String[] personnalEquipment = Arrays.copyOfRange(temp, 5, temp.length);

        String headerHTML = """
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <link rel="stylesheet" href="Style.css">
                    <title></title>
                </head>
                """;

        String identityHTML = String.format("""
                <body>
                    <div>
                        <div id="header">
                            <img src="Assets/Images/GoSecuri.png" alt="GO Securi" id="logo">
                            <a href="">nav item 1</a>
                            <a href="">nav item 2</a>
                        </div>
                        <div id="infoAgent">
                            <div id="nom">
                                <p>%s %s</p>
                                <p>Status : %s</p>
                            </div>
                                <img id="carte" src=%s>
                            <div id="itemList">
                               <p>Equipment :</p>
                               <ul>
                """, description[0], description[1], description[2], agentInfos[1]);
        String equipmentHTML = "";

        for (String equipment: personnalEquipment) {
            equipmentHTML += String.format("""
                    <li>
                        %s
                    </li>
                    """, equipmentList.get(equipment));
        }

        String endHTML = """
                                </ul>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """;

        //On crée le fichier avec les String précédement créé
        PrintWriter writer = new PrintWriter("src/site/" + agent + ".html");
        writer.println(headerHTML + identityHTML + equipmentHTML + endHTML);

        writer.close();
    }

    //Valeur d'entrée : Le chemin voulus pour le fichier index.html
    public static boolean isFile(String filePath, boolean create) throws IOException {
        File file = new File(filePath);

        //Permet de savoir si le fichier index.html existe
        //Si il n'existe pas, on le crée
        if (!file.exists()) {
            //Si create est à false, on ne crée pas le fichier et on retourne false
            if (!create) {
                return false;
            }
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        }

        return true;
    }

    //Valeur d'entrée : Le nom du agent
    public static String[] getAgentFiles(String agent) throws IOException {
        File file = new File("src/agents/"+agent);

        List<String> agentInfo = new ArrayList<>();
        //On récupère la list des fichiers
        File[] children = file.listFiles();
        String txtPath = null;
        String IdentityCard = null;

        //Si le dossier contient des fichiers, on continue
        if (children != null) {
            //Pour chaque fichier dans le dossier
            for (File child : children) {
                //Si le fichier se termine par txt, on récupère le chemin
                if (child.getAbsolutePath().substring(child.getAbsolutePath().length() - 3).equals("txt")) {
                    txtPath = child.getAbsolutePath();
                } else { //Sinon on prend le chemin du fichier image
                    IdentityCard =child.getAbsolutePath();
                }
            }
        }

        return new String[]{txtPath, IdentityCard};
    }

    //Valeur d'entrée : Le chemin vers le fichier staff.txt
    public static List<String> getAgents(String agentPath) throws IOException {
        List<String> agentList = new ArrayList<>();
        File agentFile = new File(agentPath);
        Scanner agentFileScan = new Scanner(agentFile);

        //On lit le fichier tant qu'il y a un retour à la ligne
        while(agentFileScan.hasNextLine()) {
           agentList.add(agentFileScan.nextLine());
        }
        agentFileScan.close();

        return agentList;
    }
    public static String getAgentInfo(String agentInfo) throws IOException {
        String agentInfoFile = "";

        //On lit le fichier tant qu'il y a un retour à la ligne
        if (isFile(agentInfo, false)) {
            File file = new File(agentInfo);
            Scanner infoFile = new Scanner(file);


            while (infoFile.hasNextLine()) {
                agentInfoFile += (agentInfoFile.equals("")) ?
                        infoFile.nextLine() :
                        ";" + infoFile.nextLine();
            }
            infoFile.close();
        }
        return agentInfoFile;
    }

    public static void createCSS() throws FileNotFoundException {
        //Création du fichier CSS
        PrintWriter writer = new PrintWriter("src/site/Style.css");
        writer.println("""
                body{
                    margin: unset;
                    background-color: #379fc133;
                }
                                
                #header{
                    display: flex;
                    align-items: center;
                    justify-content: left;
                   \s
                    background-color: #379EC1;
                }
                                
                #header a{
                    background-color: #659224;
                    text-decoration: none;
                    outline: none;
                    color: black;
                    font-size: 2vw;
                                
                    padding: 5px;
                }
                #header a:hover{
                    background-color: #53791e;
                    text-decoration: none;
                    outline: none;
                    color: black;
                    font-size: 2vw;
                                
                    padding: 5px;
                }
                                
                #logo{
                    height: 4vw;
                    width: auto;
                }
                                
                .menuItem{
                    margin-left: 3vw;
                                
                    background-color: #659224;
                }
                                
                #infoAgent{
                    display: flex;
                    flex-direction: column;
                                
                }
                                
                #infoAgent{
                    display: grid;
                    grid-template: "nom carte"
                    "ItemList .";
                                
                    grid-template-columns: ;
                                
                    align-items: center;
                    justify-content: space-around;
                }
                                
                .agentList{
                    display: flex;
                    flex-direction: column;
                }
                                
                #nom{
                    grid-area: "nom";
                }
                                
                #carte{
                    grid-area: "carte";
                    width: 300px;
                    height: auto;
                }
                                
                #ItemList{
                    grid-area: "ItemList";
                }
                """);
        writer.close();
    }
}
