package slpl.parse;

import slpl.Token;
import slpl.TokenType;
import slpl.util.TokenStream;

public class ArithmeticExpressionRecognizer {

    public static void recognizeArithmeticExpression(TokenStream ts) throws ParseException {
        recognizeTerm(ts);
        if(ts.hasNext(TokenType.ADD, TokenType.SUB)) {
            ts.consume();
            recognizeArithmeticExpression(ts);
        }
    }

    private static void recognizeTerm(TokenStream ts) throws ParseException {
        recognizeFactor(ts);
        if(ts.hasNext(TokenType.MUL, TokenType.DIV)) {
            ts.consume();
            recognizeTerm(ts);
        }
    }

    private static void recognizeFactor(TokenStream ts) throws ParseException {
        ts.expect(TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.SUB, TokenType.LPAR);
        if(ts.hasNext(TokenType.NUMBER, TokenType.IDENTIFIER)) {
            ts.consume();
        } else if(ts.hasNext(TokenType.SUB)) {
            Token t = ts.inspect();
            ts.replaceCurrentToken(new Token(TokenType.ADDINV, "-", t.getCol(), t.getRow()));
            ts.consume();
            recognizeFactor(ts);
        } else if(ts.hasNext(TokenType.LPAR)) {
            ts.consume();
            recognizeArithmeticExpression(ts);
            ts.expect(TokenType.RPAR);
            ts.consume();
        }
    }
}
