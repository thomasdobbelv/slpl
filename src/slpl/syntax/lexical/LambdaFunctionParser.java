package slpl.syntax.lexical;

import slpl.ast.Block;
import slpl.ast.Declaration;
import slpl.ast.LambdaFunction;
import slpl.err.ParseException;
import slpl.syntax.BlockParser;
import slpl.syntax.DeclarationParser;
import slpl.syntax.TypeParser;
import slpl.util.TokenStream;

import java.util.LinkedList;
import java.util.List;

public class LambdaFunctionParser {

    public static LambdaFunction parseLambdaFunction(TokenStream ts) throws ParseException {
        ts.expectOneOf(TokenType.LPAR);
        ts.consume();
        List<Declaration> parameters = new LinkedList<>();
        while(!ts.hasNext(TokenType.RPAR)) {
            parameters.add(DeclarationParser.parseDeclaration(ts));
            if(ts.hasNext(TokenType.COMMA)) {
                ts.consume();
            } else {
                ts.expectOneOf(TokenType.RPAR);
            }
        }
        ts.consume();
        ts.expectOneOf(TokenType.COLON);
        ts.consume();
        String returnType = TypeParser.parseType(ts);
        ts.expectOneOf(TokenType.ARROW);
        ts.consume();
        Block body = BlockParser.parseBlock(ts);
        return new LambdaFunction(parameters, returnType, body);
    }

}
