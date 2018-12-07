import java.util.List;

public class Lexer {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.err.println("Archivo no especificado. Escriba el nombre del" +
                    " archivo después del nombre del programa.");
            System.exit(1);
        }

        LexerUtil lexerUtil = new LexerUtil();
        FileUtil fileUtil = new FileUtil();

        // Ruta relativa del archivo.
        String fileName = args[0].split("[.]")[0];
        String path = "../../../resources/";

        // Eliminar archivos si existen.
        fileUtil.deleteIfExists(path + fileName + ".sim");
        fileUtil.deleteIfExists(path + fileName + ".lex");

        // Recupera líneas del archivo a evaluar.
        List<String> lines = fileUtil.readFile(path + args[0]);

        // Secciona en lexemas las líneas recuperadas.
        List<String[]> lexemes = lexerUtil.splitIntoLexemes(lines);

        // Almacena los tokens resultantes. (.lex).
        List<String> tokens = lexerUtil.getTokens(lexemes);

        // Almacena los identificadores y valores resultantes. (.sim).
        List<String> identifiersAndValues = lexerUtil.getIdentifiersAndValues(lexemes);

        // Creación y llenado de archivos.
        for(String s : identifiersAndValues) {
            fileUtil.appendToFile(s, path + fileName + ".sim");
        }
        for(String token : tokens) {
            fileUtil.appendToFile(token, path + fileName + ".lex");
        }
    }
}
