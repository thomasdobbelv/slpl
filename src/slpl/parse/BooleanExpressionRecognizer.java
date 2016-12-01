package slpl.parse;

import slpl.TokenType;
import slpl.util.TokenStream;

public class BooleanExpressionRecognizer {

    public static void recognizeBooleanExpression(TokenStream ts) throws ParseException {
        recognizeLogicalConjunction(ts);
        if (ts.hasNext(TokenType.OR)) {
            ts.consume();
            recognizeBooleanExpression(ts);
        }
    }

    private static void recognizeLogicalConjunction(TokenStream ts) throws ParseException {
        recognizeAtomicFormula(ts);
        if (ts.hasNext(TokenType.AND)) {
            ts.consume();
            recognizeLogicalConjunction(ts);
        }
    }

    private static void recognizeAtomicFormula(TokenStream ts) throws ParseException {
        if (ts.hasNext(TokenType.NOT)) {
            ts.consume();
            recognizeAtomicFormula(ts);
        } else if (ts.hasNext(TokenType.LPAR)) {
            ts.consume();
            recognizeBooleanExpression(ts);
            ts.expect(TokenType.RPAR);
            ts.consume();
        } else {
            int i = ts.getCurrentIndex();
            ArithmeticExpressionRecognizer.recognizeArithmeticExpression(ts);
            if (ts.hasNext(TokenType.EQ, TokenType.NEQ, TokenType.GTE, TokenType.LTE, TokenType.GT, TokenType.LT)) {
                ts.consume();
                ArithmeticExpressionRecognizer.recognizeArithmeticExpression(ts);
            } else {
                ts.setCurrentIndex(i);
                ts.expect(TokenType.IDENTIFIER);
                ts.consume();
            }
        }
    }

}
