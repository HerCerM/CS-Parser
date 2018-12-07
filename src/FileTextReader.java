import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTextReader {

    private final String fileName;

    public FileTextReader(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<String> readFile() {
        ArrayList<String> lines = new ArrayList<>();

        try(Scanner input = new Scanner(Paths.get(fileName));) {

            while(input.hasNextLine())
                lines.add(input.nextLine());
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return lines;
    }
}
