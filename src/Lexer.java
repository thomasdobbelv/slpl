import java.util.List;

public class Lexer {

    private static int row = 0, col = 0;

    public static List<Token> lex(String programText) {
        // TODO: Keep track of row and column numbers for precise error messages.
    }


    public static void main(String[] args) {
        String programText = "11 + 22 - 33";
        for(Token t : lex(programText)) {
            System.out.println(t);
        }
    }

}
