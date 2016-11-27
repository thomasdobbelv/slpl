package slpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {

    public static List<Token> lex(String programText) {
        StringBuilder captureGroups = new StringBuilder();
        for(TokenType tokenType : TokenType.values()) {
            captureGroups.append(String.format("|(?<%s>%s)", tokenType.name(), tokenType.pattern));
        }
        Pattern pattern = Pattern.compile(captureGroups.substring(1));
        Matcher matcher = pattern.matcher(programText);
        ArrayList<Token> tokens = new ArrayList<>();
        while(matcher.find()) {
            for(TokenType tokenType : TokenType.values()) {
                if(tokenType == TokenType.WHITESPACE) {
                    continue;
                }
                String capturedSubsequence = matcher.group(tokenType.name());
                if(capturedSubsequence != null) {
                    tokens.add(new Token(tokenType, capturedSubsequence));
                    break;
                }
            }
        }
        return tokens;
    }

    public static void main(String[] args) {
        System.out.println(lex("2 + --1"));
    }

}
