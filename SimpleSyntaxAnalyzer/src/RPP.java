import java.util.ArrayList;
import java.util.List;

public class RPP {
    private List<String> word;

    public void assessWord(ArrayList<String> word) {
        this.word = word;
        parseTp();
        System.out.println("The word is accepted");
    }

    private void parseTp() {
        String topLetter = word.get(0);
        if(topLetter.equals("a") || topLetter.equals("b") || topLetter.equals("$")) {
            parseT();
            match("$");
        }
    }

    private void parseT() {
        String topLetter = word.get(0);
        if(topLetter.equals("b") || topLetter.equals("c") || topLetter.equals("$")) {
            parseR();
        }
        else if(topLetter.equals("a")) {
            match("a");
            parseT();
            match("c");
        }
    }

    private void parseR() {
        String topLetter = word.get(0);
        if(topLetter.equals("c") || topLetter.equals("$")) {
        }
        else if(topLetter.equals("b")) {
            match("b");
            parseR();
        }
    }

    private void match(String letter) {
        if(word.size() > 0 && word.get(0).equals(letter))
            word.remove(0);
        else {
            System.err.println("ERROR: No match");
            System.exit(1);
        }
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }
}
