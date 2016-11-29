package slpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {

    public static List<Token> lex(String programText, boolean skipLayout) {
        StringBuilder captureGroups = new StringBuilder();
        for (TokenType tokenType : TokenType.values()) {
            captureGroups.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.getPattern()));
        }
        Pattern pattern = Pattern.compile(captureGroups.substring(1));
        Matcher matcher = pattern.matcher(programText);
        ArrayList<Token> tokens = new ArrayList<>();
        int row = 0, col = 0;
        while (matcher.find()) {
            for (TokenType tokenType : TokenType.values()) {
                String capturedSubsequence = matcher.group(tokenType.name());
                if (capturedSubsequence != null) {
                    tokens.add(new Token(tokenType, capturedSubsequence, row, col));
                    col += capturedSubsequence.length();
                    break;
                }
            }
        }
        return tokens;
    }
}
