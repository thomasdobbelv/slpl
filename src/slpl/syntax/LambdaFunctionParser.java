package slpl.syntax;

import slpl.ast.Block;
import slpl.ast.Declaration;
import slpl.ast.LambdaFunction;
import slpl.ast.Type;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;
import java.util.LinkedList;

public class LambdaFunctionParser {

    public static LambdaFunction parseLambdaFunction(TokenStream ts) throws ParseException {
        ts.expect(TokenType.LPAR);
        ts.consume();
        LinkedList<Declaration> paramList = new LinkedList<>();
        while(!ts.hasNext(TokenType.RPAR)) {
            paramList.add(DeclarationParser.parseDeclaration(ts));
            if(!ts.hasNext(TokenType.RPAR)) {
                ts.expect(TokenType.COMMA);
                ts.consume();
                if(ts.hasNext(TokenType.RPAR)) {
                    throw ParseException.unexpected(ts.consume());
                }
            }
        }
        ts.consume();
        ts.expect(TokenType.ARROW);
        ts.consume();
        Type returnType = TypeParser.parseType(ts);
        Block body = BlockParser.parseBlock(ts);
        Declaration[] params = new Declaration[paramList.size()];
        for(int i = 0; i < params.length; ++i) {
            params[i] = paramList.poll();
        }
        return new LambdaFunction(params, returnType, body);
    }

}
