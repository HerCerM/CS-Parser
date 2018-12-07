import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static void main(String[] args) {
        LexerUtil lexerUtil = new LexerUtil();

        // Recupera líneas del archivo a evaluar.
        List<String> lines =
                new FileTextReader("resources/factorial.mio").readFile();

        // Secciona en lexemas las líneas recuperadas.
        List<String[]> lexemes = lexerUtil.splitIntoLexemes(lines);

        // Almacena los tokens resultantes. (factorial.lex).
        List<String> tokens = lexerUtil.getTokens(lexemes);

        // Almacena los identificadores y valores resultantes. (factorial.sim).
        List<String> identifiersAndValues = lexerUtil.getIdentifiersAndValues(lexemes);


        for(String s : identifiersAndValues)
            System.out.println(s);

        System.out.println();

        for(String token : tokens)
            System.out.println(token);

    }
}
