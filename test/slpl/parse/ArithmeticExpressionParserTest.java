//package slpl.parse;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import slpl.Lexer;
//import slpl.ast.AST;
//import slpl.util.Pair;
//
//import static org.junit.Assert.*;
//
//public class ArithmeticExpressionParserTest {
//
//    private static String[] validArithmeticExpressions = {
//            "1",
//            "a",
//            "1 + 2",
//            "a + b",
//            "1 + a",
//            "1 + 3 * (4 - a) / 3 + c",
//            "10.3 + 52.3 * a - (19 / (3 * a))"
//    };
//
//    private static String[] invalidArithmeticExpressions = {
//            "a + (3 - 5",
//            "1 -",
//            "+ * 3",
//            "* 14"
//    };
//
//    @Before
//    public void setUp() throws Exception {
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }
//
//    @Test
//    public void parseArithmeticExpression() throws Exception {
//        // TODO: construct expected ast for each valid arithmetic expression and check equality
//        for(String arithmeticExpression : validArithmeticExpressions) {
//            Pair<AST, Integer> p = ArithmeticExpressionParser.parseArithmeticExpression(0, Lexer.lex(arithmeticExpression));
//            System.out.printf("AST: %s\n", p.fst);
////            System.out.printf("Evaluation result: %s", p.fst.evaluate());
//        }
////        for(String arithmeticExpression : invalidArithmeticExpressions) {
////            try {
////                ArithmeticExpressionParser.parseArithmeticExpression(0, Lexer.lex(arithmeticExpression));
////                fail(String.format("invalid arithmetic expression \"%s\" passed to %s, but no instance of %s thrown",
////                        arithmeticExpression, ArithmeticExpressionParser.class, ParseException.class));
////            } catch (ParseException e) {
////                // OK
////            }
////        }
//    }
//
//    @Test
//    public void recognizeArithmeticExpression() throws Exception {
//
//    }
//
//    @Test
//    public void recognizeTerm() throws Exception {
//
//    }
//
//    @Test
//    public void recognizeFactor() throws Exception {
//
//    }
//
//    @Test
//    public void infixToRPN() throws Exception {
//
//    }
//
//}