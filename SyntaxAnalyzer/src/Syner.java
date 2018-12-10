import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Syner {
    public static void main(String[] args) {
        RPP rpp = new RPP();

        FileUtil fileUtil = new FileUtil();

        ArrayList<String> word = fileUtil.readFile("src/word.lex");

        rpp.assessWord(word);
    }
}
