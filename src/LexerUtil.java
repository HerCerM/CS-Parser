import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if(identifier.matches("[A-Za-z][[A-Za-z]*+[0-9]*]*")
                && identifier.length() <= 16
                && identifier.length() > 0)
            valid = true;

        return valid;
    }

    private boolean isHexValue(String value) {
        boolean valid = false;

        if(value.matches("0x([0-9]|[A-F])*"))
            valid = true;

        return valid;
    }

    private boolean isTextValue(String value) {
        boolean valid = false;

        if(value.matches("\"([A-Za-z]|[0-9]|\\s)*\""))
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
        ArrayList<String[]> lexemes = new ArrayList<>();

        // Secciona cadenas en lexemas.
        for(String line : lines) {
            if (line.contains("\"")) {
                String delimiter = "[^\\s]*\"[^\"]*\"[^\\s]*|\\s";
                lexemes.add(splitWithDelimiters(line, delimiter, " "));
            }
            else {
                lexemes.add(line.split(" "));
            }
        }
        return lexemes;
    }

    private ArrayList<String> extractTokens(List<String[]> lexemes)
            throws InvalidTokenException {
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
        if(identifiersAndValues == null)
            identifiersAndValues = buildIdentifiersAndValues(lexemes);
        return (ArrayList<String>) identifiersAndValues;
    }

    private static String[] splitWithDelimiters(String line,
                                                String regex,
                                                String excludedDelimiter) {
        List<String> parts = new ArrayList<String>();

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        int lastEnd = 0;
        while(matcher.find()) {
            int start = matcher.start();
            if(lastEnd != start) {
                String nonDelimiter = line.substring(lastEnd, start);
                parts.add(nonDelimiter);
            }

            String delimiter = matcher.group();
            if(!delimiter.equals(excludedDelimiter))
                parts.add(delimiter);

            lastEnd = matcher.end();
        }

        if(lastEnd != line.length()) {
            String nonDelim = line.substring(lastEnd);
            parts.add(nonDelim);
        }

        String[] res =  parts.toArray(new String[]{});
        System.out.println("result: " + Arrays.toString(res));

        return res;
    }
}
