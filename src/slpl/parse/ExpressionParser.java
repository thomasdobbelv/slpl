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
//        ArithmeticExpressionRecognizer.recognizeArithmeticExpression(ts);
        BooleanExpressionRecognizer.recognizeBooleanExpression(ts);
    }
}
