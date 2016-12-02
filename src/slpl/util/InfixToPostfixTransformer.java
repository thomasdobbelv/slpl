package slpl.util;

import slpl.syntax.lexical.Token;
import slpl.syntax.lexical.TokenType;
import slpl.syntax.lexical.TokenTypeClass;
import slpl.syntax.ParseException;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InfixToPostfixTransformer {

    /**
     * Dijkstra's Shunting Yard algorithm.
     */
    public static List<Token> transform(TokenStream ts, int end) throws ParseException {
        LinkedList<Token> outputQueue = new LinkedList<>();
        Stack<Token> operatorStack = new Stack<>();
        while (ts.getCurrentIndex() != end) {
            Token t = ts.consume();
            if (t.isValue()) {
                outputQueue.add(t);
            } else if (t.isOperator()) {
                enqueuePrecedentOperators(outputQueue, operatorStack, Operator.fromToken(t));
                operatorStack.push(t);
            } else if (t.getType() == TokenType.LPAR) {
                operatorStack.push(t);
            } else if (t.getType() == TokenType.RPAR) {
                try {
                    while (!operatorStack.peek().getContent().equals("(")) {
                        outputQueue.add(operatorStack.pop());
                    }
                    operatorStack.pop();
                } catch (EmptyStackException e) {
                    throw ParseException.bracketMismatch(t);
                }
            }
        }
        while (!operatorStack.isEmpty()) {
            if (operatorStack.peek().getType().instanceOf(TokenTypeClass.BRACKET)) {
                throw ParseException.bracketMismatch(operatorStack.peek());
            }
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

    /**
     * Add to <b>outputQueue</b> every operator on <b>operatorStack</b> that has a higher precedence than <b>o1</b>.
     *
     * @param outputQueue
     * @param operatorStack
     * @param o1
     */
    private static void enqueuePrecedentOperators(LinkedList<Token> outputQueue, Stack<Token> operatorStack, Operator o1) {
        while (!operatorStack.isEmpty() && operatorStack.peek().isOperator()) {
            Operator o2 = Operator.fromToken(operatorStack.peek());
            if (o1.getFixity() == Operator.Fixity.LEFT && o1.getPrecedence() <= o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else if (o1.getFixity() == Operator.Fixity.RIGHT && o1.getPrecedence() < o2.getPrecedence()) {
                outputQueue.add(operatorStack.pop());
            } else {
                break;
            }
        }
    }
}
