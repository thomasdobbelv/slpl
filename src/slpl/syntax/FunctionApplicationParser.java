package slpl.syntax;

import slpl.ast.AST;
import slpl.ast.FunctionApplication;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

import java.util.LinkedList;

public class FunctionApplicationParser {

    public static FunctionApplication parseFunctionApplication(TokenStream ts) throws ParseException {
        ts.expectOneOf(TokenType.FID);
        String name = ts.consume().content();
        ts.expectOneOf(TokenType.LPAR);
        ts.consume();
        LinkedList<AST> argList = new LinkedList<>();
        while (!ts.hasNext(TokenType.RPAR)) {
            argList.add(ExpressionParser.parseExpression(ts));
            if (!ts.hasNext(TokenType.RPAR)) {
                ts.expectOneOf(TokenType.COMMA);
                ts.consume();
                if(ts.hasNext(TokenType.RPAR)) {
                    throw ParseException.unexpected(ts.consume());
                }
            }
        }
        ts.consume();
        AST[] args = new AST[argList.size()];
        for(int i = 0; i < args.length; ++i) {
            args[i] = argList.poll();
        }
        return new FunctionApplication(name, args);
    }

}
