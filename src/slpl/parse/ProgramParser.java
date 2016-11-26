package slpl.parse;

import slpl.Lexer;
import slpl.Token;
import slpl.ast.Ast;
import slpl.util.Pair;

import java.util.List;

public class ProgramParser {

    public static Ast parseProgram(String programText) throws ParseException {
        List<Token> tokens = Lexer.lex(programText);
        Pair<Ast, Integer> p = ArithmeticExpressionParser.parseArithmeticExpression(0, tokens);
        if(p.snd < tokens.size()) {
            throw new ParseException(String.format("unexpected token \"%s\"", tokens.get(p.snd)));
        }
        return p.fst;
    }

    public static void main(String[] args) throws ParseException {
//        Ast ast = parseProgram("131 - (14 - 2) + 3 * 14 / (2 + 6 * 3)");
//        Ast ast = parseProgram("131 - (14 - 2) + 2.1");
        Ast ast = parseProgram("(131 - (14 - 2)) + 2.1");
        System.out.println(ast);
        System.out.println("evaluated: ");
        System.out.println(ast.evaluate());
    }

}
