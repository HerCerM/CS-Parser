import java.util.*;

public class LexerUtil {

    private final String[] keywords;
    private final String[] operators;
    private final char commentMark;

    private Map<String, String> textValues;
    private Map<String, String> identifiers;
    private Map<String, String> hexValues;

    private List<String> tokens;
    private List<String> identifiersAndValues;

    public LexerUtil() {
        keywords = new String[] {"PROGRAMA", "FINPROG", "SI", "ENTONCES",
        "SINO", "FINSI", "REPITE", "VECES", "FINREP", "IMPRIME", "LEE"};
        operators = new String[] {"+", "-", "/", "*"};
        commentMark = '#';

        identifiers = new LinkedHashMap<>();
        textValues = new LinkedHashMap<>();
        hexValues = new LinkedHashMap<>();
    }

    private boolean isKeyword(String keyword) {
        boolean valid = false;

        if(Arrays.asList(keywords).contains(keyword))
            valid = true;

        return valid;
    }

    private boolean isIdentifier(String identifier) {
        boolean valid = false;

        if(identifier.length() <= 16 && Character.isLetter(identifier.charAt(0)))
            valid = true;

        return valid;
    }

    private boolean isHexValue(String value) {
        boolean valid = false;

        try {
            if(value.substring(0, 2).equals("0x")) {
                Integer.parseInt(value.substring(2, value.length()), 16);
                valid = true;
            }
        }
        catch(NumberFormatException ignored) {}
        finally {
            return valid;
        }
    }

    private boolean isTextValue(String value) {
        boolean valid = false;

        if(value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')
            valid = true;

        return valid;
    }

    private boolean isOperator(String operator) {
        boolean valid = false;

        if(Arrays.asList(operators).contains(operator))
            valid = true;

        return valid;
    }

    public ArrayList<String[]> splitIntoLexemes(List<String> lines) {
        lines = (ArrayList<String>) lines;
        ArrayList<String[]> lexemes = new ArrayList<>();

        // Secciona cadenas en lexemas.
        for(String line : lines) {
            if (line.contains("\"")) {
                String[] splittedLines = line.split("(?=\")");
                ArrayList<String> processedLines = new ArrayList<>();
                ArrayList<String> finalSplit = new ArrayList<>();

                for(int i = 0; i < splittedLines.length; i++) {
                    if(!splittedLines[i].contains("\""))
                        processedLines.add(splittedLines[i].trim());
                    else
                    if(i < splittedLines.length - 1)
                        processedLines.add(splittedLines[i] +
                                splittedLines[i++ + 1]);
                    else
                        processedLines.add(splittedLines[i]);
                }

                for(String string : processedLines)
                    if(string.contains("\""))
                        finalSplit.add(string);
                    else {
                        String[] split = string.split(" ");
                        for(String s : split)
                            finalSplit.add(s);
                    }

                lexemes.add(finalSplit.toArray(new String[0]));
            }
            else {
                lexemes.add(line.split(" "));
            }
        }
        return lexemes;
    }

    private ArrayList<String> extractTokens(List<String[]> lexemes)
            throws InvalidTokenException {
        lexemes = (ArrayList<String[]>) lexemes;
        ArrayList<String> tokens = new ArrayList<>();

        for(int i = 0; i < lexemes.size(); i++)
            for(String lexeme : lexemes.get(i)) {
                if(!lexeme.equals(""))
                    if(lexeme.charAt(0) != commentMark) {
                        tokens.add(assessLexeme(lexeme, i + 1));
                    } else
                        break;
            }
        return tokens;
    }

    public ArrayList<String> getTokens(List<String[]> lexemes) {
        lexemes = (ArrayList<String[]>) lexemes;

        try {
            if(tokens == null)
                tokens = extractTokens(lexemes);
        }
        catch (InvalidTokenException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return (ArrayList<String>) tokens;
    }

    private String assessLexeme(String lexeme, int line)
            throws InvalidTokenException {
        String token = null;
        boolean validToken = false;


        if(isKeyword(lexeme)) {
            token = lexeme;
            validToken = true;
        }
        else if(isIdentifier(lexeme)) {
            token = "[id]";

            if(!identifiers.containsKey(lexeme))
                identifiers.put(lexeme, getIdentifiersMark());

            validToken = true;
        }
        else if(lexeme.equals("=")) {
            token = "=";
            validToken = true;
        }
        else if(isHexValue(lexeme)) {
            token = "[val]";

            if(!hexValues.containsKey(lexeme))
                hexValues.put(lexeme, Integer.toString(Integer.parseInt(lexeme.
                        substring(2, lexeme.length()))));

            validToken = true;
        }
        else if(isTextValue(lexeme)) {
            token = "[txt]";

            if(!textValues.containsKey(lexeme))
                textValues.put(lexeme, getTextValuesMark());

            validToken = true;
        }
        else if(isOperator(lexeme)) {
            token = "[op_ar]";
            validToken = true;
        }

        if(!validToken) {
            throw new InvalidTokenException(
                    Integer.toString(line));
        }
        return token;
    }

    private String getTextValuesMark() {
        String mark = null;

        if(textValues.size() < 10) {
            mark = "TX0" + (textValues.size() + 1);
        }
        else
            mark = "TX" + (textValues.size() + 1);

        return mark;
    }

    private String getIdentifiersMark() {
        String mark = null;

        if(identifiers.size() < 10) {
            mark = "ID0" + (identifiers.size() + 1);
        }
        else
            mark = "ID" + (identifiers.size() + 1);

        return mark;
    }

    private ArrayList<String> buildIdentifiersAndValues(List<String[]> lexemes) {
        lexemes = (ArrayList<String[]>) lexemes;
        getTokens(lexemes);

        ArrayList<String> identifiersAndValues = new ArrayList<>();

        identifiersAndValues.add("IDS");

        for(Map.Entry<String, String> mapElement : identifiers.entrySet())
            identifiersAndValues.add(String.format("%s,%s",
                    mapElement.getKey(), mapElement.getValue()));

        identifiersAndValues.add("\nTXT");

        for(Map.Entry<String, String> mapElement : textValues.entrySet())
            identifiersAndValues.add(String.format("%s,%s",
                    mapElement.getKey(), mapElement.getValue()));

        identifiersAndValues.add("\nVAL");

        for(Map.Entry<String, String> mapElement : hexValues.entrySet())
            identifiersAndValues.add(String.format("%s,%s",
                    mapElement.getKey(), mapElement.getValue()));


        return identifiersAndValues;
    }

    public ArrayList<String> getIdentifiersAndValues(List<String[]> lexemes) {
        lexemes = (ArrayList<String[]>) lexemes;

        if(identifiersAndValues == null)
            identifiersAndValues = buildIdentifiersAndValues(lexemes);
        return (ArrayList<String>) identifiersAndValues;
    }
}
