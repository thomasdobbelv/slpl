package slpl.syntax;

import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.StringConcatenator;
import slpl.util.TokenStream;
import java.util.LinkedList;

public class TypeParser {

    public static String parseType(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.ID)) {
            return ts.consume().content();
        }
        ts.expect(TokenType.LPAR);
        ts.consume();
        LinkedList<String> paramTypes = new LinkedList<>();
        while(!ts.hasNext(TokenType.RPAR)) {
            paramTypes.add(parseType(ts));
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
        String domain = StringConcatenator.concatenate(", ", paramTypes.toArray());
        String codomain = parseType(ts);
        return String.format("(%s) -> %s", domain, codomain);
    }

}
