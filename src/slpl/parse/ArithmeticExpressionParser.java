package slpl.parse;

import slpl.Operator;
import slpl.Token;
import slpl.TokenType;
import slpl.ast.*;
import slpl.ast.Number;
import slpl.util.InfixToPostfixTransformer;
import slpl.util.Pair;

import java.util.List;
import java.util.Stack;

public class ArithmeticExpressionParser {

    public static Pair<AST, Integer> parseArithmeticExpression(int start, List<Token> tokens) throws ParseException {
        int end = recognizeArithmeticExpression(start, tokens);
        Stack<AST> s = new Stack<>();
        for (Token t : InfixToPostfixTransformer.transform(tokens.subList(start, end))) {
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
        return new Pair<>(s.pop(), end);
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

    public static int recognizeArithmeticExpression(int start, List<Token> tokens) throws ParseException {
        int end = recognizeTerm(start, tokens);
        if (end < tokens.size() && (tokens.get(end).getContent().equals("+") || tokens.get(end).getContent().equals("-"))) {
            end = recognizeArithmeticExpression(end + 1, tokens);
        }
        return end;
    }

    public static int recognizeTerm(int start, List<Token> tokens) throws ParseException {
        int end = recognizeFactor(start, tokens);
        if (end < tokens.size() && (tokens.get(end).getContent().equals("*") || tokens.get(end).getContent().equals("/"))) {
            end = recognizeTerm(end + 1, tokens);
        }
        return end;
    }

    public static int recognizeFactor(int start, List<Token> tokens) throws ParseException {
        Token t = tokens.get(start);
        if (t.isValue()) {
            return start + 1;
        } else if (t.getType() == TokenType.SUB) {
            // t must be the unary additive inverse operator
            tokens.set(start, new Token(TokenType.ADDINV, "-", t.getCol(), t.getRow()));
            return recognizeFactor(start + 1, tokens);
        } else if (t.getType() == TokenType.LPAR) {
            int end = recognizeArithmeticExpression(start + 1, tokens);
            if (tokens.get(end).getType() != TokenType.RPAR) {
                throw ParseException.expected(TokenType.RPAR, tokens.get(end - 1));
            }
            return end + 1;
        } else {
            throw ParseException.unexpected(t);
        }
    }
}
