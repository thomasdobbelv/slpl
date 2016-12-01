package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.TokenTypeClass;
import slpl.ast.*;
import slpl.ast.Boolean;
import slpl.ast.Number;
import slpl.util.InfixToPostfixTransformer;
import slpl.util.TokenStream;
import java.util.Stack;

public class ExpressionParser {

    public static AST parseExpression(TokenStream ts) throws ParseException {
        int start = ts.getCurrentIndex();
        recognizeExpression(ts);
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
                s.push(toOperationNode(t, operands));
            } else {
                s.push(toValueNode(t));
            }
        }
        assert ts.getCurrentIndex() == end;
        return s.pop();
    }

    public static AST toOperationNode(Token t, AST[] operands) {
        TokenType tt = t.getType();
        if(tt.instanceOf(TokenTypeClass.UNARY_OPERATOR)) {
            if(tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new UnaryArithmeticOperation(t.getContent(), operands[0]);
            } else if(tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new UnaryLogicalOperation(t.getContent(), operands[0]);
            }
        } else if(tt.instanceOf(TokenTypeClass.BINARY_OPERATOR)) {
            if(tt.instanceOf(TokenTypeClass.ARITHMETIC_OPERATOR)) {
                return new BinaryArithmeticOperation(t.getContent(), operands[0], operands[1]);
            } else if(tt.instanceOf(TokenTypeClass.LOGICAL_OPERATOR)) {
                return new BinaryLogicalOperation(t.getContent(), operands[0], operands[1]);
            } else if(tt.instanceOf(TokenTypeClass.RELATIONAL_OPERATOR)) {
                return new RelationalOperation(t.getContent(), operands[0], operands[1]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(AST a : operands) {
            sb.append(a + " ");
        }
        throw new IllegalArgumentException(String.format("The operation %s is not defined for %s", t, sb.toString()));
    }

    public static AST toValueNode(Token t) {
        switch (t.getType()) {
            case TRUE:
                return new Boolean(true);
            case FALSE:
                return new Boolean(false);
            case IDENTIFIER:
                return new Identifier(t.getContent());
            case NUMBER:
                return new Number(t.getContent());
        }
        throw new IllegalArgumentException(t + " is not a value");
    }

    public static void recognizeExpression(TokenStream ts) throws ParseException {
        recognizeTerm(ts);
        if(ts.hasNext(TokenType.ADD, TokenType.SUB, TokenType.OR) || (ts.hasNext() && ts.inspect().getType().instanceOf(TokenTypeClass.RELATIONAL_OPERATOR))) {
            ts.consume();
            recognizeExpression(ts);
        }
    }

    private static void recognizeTerm(TokenStream ts) throws ParseException {
        recognizeFactor(ts);
        if(ts.hasNext(TokenType.MUL, TokenType.DIV, TokenType.AND)) {
            ts.consume();
            recognizeTerm(ts);
        }
    }

    private static void recognizeFactor(TokenStream ts) throws ParseException {
        ts.expect(TokenType.NOT, TokenType.SUB, TokenType.LPAR, TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.TRUE, TokenType.FALSE);
        if(ts.hasNext(TokenType.NOT, TokenType.SUB)) {
            if(ts.hasNext(TokenType.SUB)) {
                Token t = ts.inspect();
                ts.replaceCurrentToken(new Token(TokenType.ADDINV, t.getContent(), t.getRow(), t.getCol()));
            }
            ts.consume();
            recognizeFactor(ts);
        } else if(ts.hasNext(TokenType.LPAR)) {
            ts.consume();
            recognizeExpression(ts);
            ts.expect(TokenType.RPAR);
            ts.consume();
        } else {
            ts.consume();
        }
    }
}
