import java.util.ArrayList;
import java.util.List;

public class RPP {
    private List<String> word;

    public void assessWord(ArrayList<String> word) {
        this.word = word;
        parsePROG();
        System.out.println("The word is accepted");
    }

    private void match(String letter) {
        if(word.size() > 0 && word.get(0).equals(letter))
            word.remove(0);
        else {
            error();
        }
    }

    private void parsePROG() {
        match("PROGRAMA");
        match("[id]");
        parseSENT();
        match("FINPROG");
    }

    private void parseSENT() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]")) {
            match("[id]");
            parseFID();
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
        else if(topLetter.equals("SI")) {
            match("SI");
            parseFSI();
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
        else if(topLetter.equals("REPITE")) {
            match("REPITE");
            parseELEM();
            match("VECES");
            parseSENT();
            match("FINREP");
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
        else if(topLetter.equals("IMPRIME")) {
            match("IMPRIME");
            parseFIMPRIME();
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
        else if(topLetter.equals("LEE")) {
            match("LEE");
            match("[id]");
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
        else if(topLetter.equals("#")) {
            match("#");
            match("[comentario]");
            if(!topLetter.equals("FINPROG")) {
                parseSENT();
            }
        }
    }

    private void parseFID() {
        match("=");
        parseFE();
    }

    private void parseFE() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]") || topLetter.equals("[val]")) {
            parseELEM();
            parseFELEM();
        }
        else {
            error();
        }
    }

    private void parseFELEM() {
        String topLetter = word.get(0);
        if(topLetter.equals("[op_ar]")) {
            match("[op_ar]");
            parseELEM();
        }
    }

    private void parseFSI() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]")) {
            parseCOMPARA();
            parseFCOMPARA();
        }
    }

    private void parseFCOMPARA() {
        match("ENTONCES");
        parseFENTONCES();
    }

    private void parseFENTONCES() {
        String topLetter = word.get(0);
        parseSENT();
        parseFSENTS();
    }

    private void parseFSENTS() {
        String topLetter = word.get(0);
        if(topLetter.equals("SINO")) {
            match("SINO");
            parseSENT();
            match("FINSI");
        }
        else
            match("FINSI");
    }

    private void parseFIMPRIME() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]") || topLetter.equals("[val]"))
            parseELEM();
        else if(topLetter.equals("[txt]"))
            match("[txt]");
        else
            error();
    }

    private void parseELEM() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]"))
            match("[id]");
        else if(topLetter.equals("[val]"))
            match("[val]");
        else
            error();
    }

    private void parseCOMPARA() {
        String topLetter = word.get(0);
        if(topLetter.equals("[id]")) {
            match("[id]");
            match("[op_rel]");
            parseELEM();
        }
    }

    private void error() {
        System.err.println("ERROR: No match");
        System.exit(1);
    }

    public List<String> getWord() {
        return word;
    }

    public void setWord(List<String> word) {
        this.word = word;
    }
}
