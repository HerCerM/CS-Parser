import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static void main(String[] args) {
        LexerUtil lexerUtil = new LexerUtil();

        // Recupera líneas del archivo a evaluar.
        ArrayList<String> lines =
                new FileTextReader("resources/factorial.mio").readFile();

        // Secciona en lexemas las líneas recuperadas.
        ArrayList<String[]> lexemes = lexerUtil.splitIntoLexemes(lines);

        // Almacena los tokens resultantes. (factorial.lex).
        ArrayList<String> tokens = lexerUtil.getTokens(lexemes);

        // Almacena los identificadores y valores resultantes. (factorial.sim).
        List<String> identifiersAndValues = lexerUtil.getIdentifiersAndValues(lexemes);


        for(String s : identifiersAndValues)
            System.out.println(s);

        System.out.println();

        for(String token : tokens)
            System.out.println(token);

    }
}
