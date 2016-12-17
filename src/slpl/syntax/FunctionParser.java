package slpl.syntax;

import slpl.ast.Block;
import slpl.ast.Declaration;
import slpl.ast.Function;
import slpl.ast.Type;
import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

import java.util.LinkedList;

public class FunctionParser {

    public static Function parseFunction(TokenStream ts) throws ParseException {
        ts.expect(TokenType.FUNCTION);
        ts.consume();
        ts.expect(TokenType.ID);
        String name = ts.consume().content();
        LinkedList<Declaration> paramList = new LinkedList<>();
        ts.expect(TokenType.LPAR);
        ts.consume();
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
        Declaration[] params = new Declaration[paramList.size()];
        for(int i = 0; i < params.length; ++i) {
            params[i] = paramList.poll();
        }
        ts.consume();
        ts.expect(TokenType.COLON);
        ts.consume();
        Type type = TypeParser.parseType(ts);
        Block body = BlockParser.parseBlock(ts);
        return new Function(name, params, type, body);
    }

}
