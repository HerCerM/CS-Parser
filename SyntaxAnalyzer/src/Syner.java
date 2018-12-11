import java.util.ArrayList;

public class Syner {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.err.println("Archivo no especificado. Escriba el nombre del" +
                    " archivo después del nombre del programa.");
            System.exit(1);
        }


        RPP rpp = new RPP();
        FileUtil fileUtil = new FileUtil();

        // Ruta relativa del archivo.

        String fileName = args[0].split("[.]")[0];
        String path = "";

        ArrayList<String> word = fileUtil.readFile(path + fileName + ".lex");


        //ArrayList<String>word = fileUtil.readFile("out/production/SyntaxAnalyzer/factorial.lex");

        rpp.assessWord(word);
    }
}
