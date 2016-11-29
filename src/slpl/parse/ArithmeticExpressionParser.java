package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.*;
import slpl.ast.Number;
import slpl.util.InfixToPostfixTransformer;
import slpl.util.TokenStream;

import java.util.Stack;

public class ArithmeticExpressionParser {

    public static AST parseArithmeticExpression(TokenStream ts) throws ParseException {
        int start = ts.getCurrentIndex();
        recognizeArithmeticExpression(ts);
        int end = ts.getCurrentIndex();
        ts.setCurrentIndex(start);
        Stack<AST> s = new Stack<>();
        for (Token t : InfixToPostfixTransformer.transform(ts, end)) {
            if (t.isOperator()) {
                int arity = Operator.fromToken(t).getArity();
                AST[] operands = new AST[arity];
                for (int i = arity - 1; i >= 0; --i) {
                    operands[i] = s.pop();
                }
                s.push(toArithmeticOperationAST(t, operands));
            } else {
                s.push(toValueAST(t));
            }
        }
        assert ts.getCurrentIndex() == end;
        return s.pop();
    }

    private static AST toArithmeticOperationAST(Token t, AST[] operands) {
        switch (t.getType().getTypeClass()) {
            case UNARY_OPERATOR:
                return new UnaryArithmeticOperation(t.getContent(), operands[0]);
            case BINARY_OPERATOR:
                return new BinaryArithmeticOperation(t.getContent(), operands[0], operands[1]);
        }
        return null;
    }

    private static AST toValueAST(Token t) {
        switch (t.getType()) {
            case NUMBER:
                return new Number(t.getContent());
            case IDENTIFIER:
                return new Identifier(t.getContent());
        }
        return null;
    }

    public static void recognizeArithmeticExpression(TokenStream ts) throws ParseException {
        recognizeTerm(ts);
        if(ts.hasNext(TokenType.ADD, TokenType.SUB)) {
            ts.consume();
            recognizeArithmeticExpression(ts);
        }
    }

    public static void recognizeTerm(TokenStream ts) throws ParseException {
        recognizeFactor(ts);
        if(ts.hasNext(TokenType.MUL, TokenType.DIV)) {
            ts.consume();
            recognizeTerm(ts);
        }
    }

    public static void recognizeFactor(TokenStream ts) throws ParseException {
        ts.expectOneOf(TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.SUB, TokenType.LPAR);
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
            ts.expectOneOf(TokenType.RPAR);
            ts.consume();
        }
    }
}
