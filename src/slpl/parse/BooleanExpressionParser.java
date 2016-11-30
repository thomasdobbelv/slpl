package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.*;
import slpl.ast.Boolean;
import slpl.util.InfixToPostfixTransformer;
import slpl.util.TokenStream;

import java.util.Stack;

public class BooleanExpressionParser {


    public static AST parseBooleanExpression(TokenStream ts) throws ParseException {
        int start = ts.getCurrentIndex();
        recognizeBooleanExpression(ts);
        int end = ts.getCurrentIndex();
        ts.setCurrentIndex(start);
        Stack<AST> s = new Stack<>();

        // TODO: rm
        System.out.println("Postfix: " + InfixToPostfixTransformer.transform(ts, end));
        ts.setCurrentIndex(start);

        for (Token t : InfixToPostfixTransformer.transform(ts, end)) {
            // TODO: check if t belongs to an arithmetic operation
            if (t.isOperator()) {
                int arity = Operator.fromToken(t).getArity();
                AST[] operands = new AST[arity];
                for (int i = arity - 1; i >= 0; --i) {
                    operands[i] = s.pop();
                }
                s.push(toBooleanOperationAST(t, operands));
            } else {
                s.push(toValueAST(t));
            }
        }
        assert ts.getCurrentIndex() == end;
        return s.pop();
    }

    private static AST toValueAST(Token t) {
        switch (t.getType()) {
            case TRUE:
                return new Boolean(true);
            case FALSE:
                return new Boolean(false);
            case IDENTIFIER:
                return new Identifier(t.getContent());
        }
        return null;
    }

    private static AST toBooleanOperationAST(Token t, AST[] operands) {
        switch (t.getType().getTypeClass()) {
            case UNARY_OPERATOR:
                return new UnaryBooleanOperation(t.getContent(), operands[0]);
            case BINARY_OPERATOR:
                return new BinaryBooleanOperation(t.getContent(), operands[0], operands[1]);
        }
        return null;
    }

    private static void recognizeBooleanExpression(TokenStream ts) throws ParseException {
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
            AST a0 = ArithmeticExpressionParser.parseArithmeticExpression(ts);
            if (ts.hasNext(TokenType.EQ, TokenType.NEQ, TokenType.GTE, TokenType.LTE, TokenType.GT, TokenType.LT)) {
                ts.consume();
                AST a1 = ArithmeticExpressionParser.parseArithmeticExpression(ts);
            } else {
                ts.setCurrentIndex(i);
                ts.expect(TokenType.IDENTIFIER);
                ts.consume();
            }
        }
    }

}
