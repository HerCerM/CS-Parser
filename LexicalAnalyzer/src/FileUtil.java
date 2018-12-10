import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {

    public ArrayList<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try(Scanner input = new Scanner(Paths.get(filePath));) {

            while(input.hasNextLine())
                lines.add(input.nextLine());
        }
        catch (NoSuchFileException e) {
            System.err.println(filePath + " no encontrado");
            System.exit(1);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return (ArrayList<String>) lines;
    }

    public void appendToFile(String append, String filePath) {
        try {
            Files.write(Paths.get(filePath), (append + System.lineSeparator()).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean deleteIfExists(String filePath) {
        boolean deleted = false;

        try {
            deleted = Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleted;
    }
}
