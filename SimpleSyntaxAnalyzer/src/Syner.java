import java.util.ArrayList;
import java.util.Arrays;

public class Syner {
    public static void main(String[] args) {
        RPP rpp = new RPP();

        String[] word = new String[] {"a", "a", "a", "b", "c", "c", "c", "$"};

        rpp.assessWord(new ArrayList<>(Arrays.asList(word)));
    }
}
