package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.AST;
import slpl.ast.Statement;
import slpl.util.Pair;
import slpl.util.TokenStream;

import java.util.List;

public class ProgramParser {

    public static AST parseProgram(String programText) throws ParseException {
        List<Token> tokens = Lexer.lex(programText, true);
        TokenStream tokenStream = new TokenStream(tokens);
        try {
            Pair<Statement, Integer> p = StatementParser.parseStatement(0, tokens);
            Statement s = StatementParser.parseStatement(0, tokenStream);
            if(p.snd < tokens.size()) {
                throw ParseException.unexpected(tokens.get(p.snd));
            }
            return p.fst;
        } catch (IndexOutOfBoundsException e) {
            throw ParseException.unexpectedEOF(tokens.get(tokens.size()-1));
        }
    }

    public static void main(String[] args) throws ParseException {
        String programText = "15 + true /";
        AST program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
