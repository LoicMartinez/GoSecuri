import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;  // Import the IOException class to handle errors

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        File index = new File("src/site/index.html");
        try {
            if (index.createNewFile()) {
                System.out.println("File created: " + index.getName());
            }
            System.out.println("patch : " + index.getAbsolutePath());
            Scanner obj = new Scanner(index);
            while (obj.hasNextLine()) {
                System.out.println(obj.nextLine());
            }
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
}