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
        String programText = "11.5 + 3/3 - 14 + (2*(2-1)/2) + 0.5 - 0 * 28 + 3*1.15";
//        System.out.println(Lexer.lex(programText));
//        System.out.println(ArithmeticExpressionParser.parseArithmeticExpression(0, Lexer.lex(programText)).fst.evaluate());
//        String programText = "2 - -1";
        Ast program = parseProgram(programText);
        System.out.println("Program: ");
        System.out.println(program);
        System.out.println("Program evaluated: ");
        System.out.println(program.evaluate());
    }

}
