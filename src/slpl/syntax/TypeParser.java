package slpl.syntax;

import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class TypeParser {

    public static String parseType(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.LPAR)) {
            StringBuilder sb = new StringBuilder();
            sb.append(ts.consume().content());
            if(!ts.hasNext(TokenType.RPAR)) {
                while(!ts.hasNext(TokenType.RPAR)) {
                    sb.append(TypeParser.parseType(ts));
                    if(ts.hasNext(TokenType.COMMA)) {
                        sb.append(ts.consume().content());
                    } else {
                        ts.expectOneOf(TokenType.RPAR);
                    }
                }
                sb.setLength(sb.length() - 1);
            }
            ts.expectOneOf(TokenType.RPAR);
            sb.append(ts.consume().content());
            ts.expectOneOf(TokenType.ARROW);
            sb.append(ts.consume().content());
            sb.append(TypeParser.parseType(ts));
            return sb.toString();
        } else {
            ts.expectOneOf(TokenType.ID);
            return ts.consume().content();
        }
    }

}
