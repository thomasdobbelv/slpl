package slpl.syntax;

import slpl.err.ParseException;
import slpl.syntax.lexical.TokenType;
import slpl.util.TokenStream;

public class TypeParser {

    public static String parseType(TokenStream ts) throws ParseException {
        if(ts.hasNext(TokenType.LPAR)) {
            StringBuilder sb = new StringBuilder();
            sb.append(ts.consume().getContent());
            if(!ts.hasNext(TokenType.RPAR)) {
                while(!ts.hasNext(TokenType.RPAR)) {
                    sb.append(TypeParser.parseType(ts));
                    if(ts.hasNext(TokenType.COMMA)) {
                        sb.append(ts.consume().getContent());
                    } else {
                        ts.expect(TokenType.RPAR);
                    }
                }
                sb.setLength(sb.length() - 1);
            }
            ts.expect(TokenType.RPAR);
            sb.append(ts.consume().getContent());
            ts.expect(TokenType.ARROW);
            sb.append(ts.consume().getContent());
            sb.append(TypeParser.parseType(ts));
            return sb.toString();
        } else {
            ts.expect(TokenType.ID);
            return ts.consume().getContent();
        }
    }

}
