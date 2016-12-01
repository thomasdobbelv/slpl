package slpl.parse;

import slpl.Lexer;
import slpl.ast.AST;
import slpl.util.TokenStream;

public class ProgramParser {

    public static AST parseProgram(String programText) throws ParseException {
        TokenStream ts = new TokenStream(Lexer.lex(programText, true));
        AST module = ModuleParser.parseModule(ts);
        if(ts.hasNext()) {
            throw ParseException.unexpected(ts.consume());
        }
        return module;
    }

    public static void main(String[] args) throws ParseException {

        String programText = "" +
                "module IfStatements {\n" +
                "    println \"Entering module IfStatements\";\n" +
                "    println \"Checking whether (4 + 18)/11 - 2 == 0 (spoiler: it is)\";\n" +
                "    if((4 + 18)/11 - 2 == 0) {\n" +
                "        println \"(4 + 18)/11 - 2 == 0 indeed\";\n" +
                "    } else {\n" +
                "        println \"Won't arrive here\";\n" +
                "    }\n" +
                "    println \"Moving on\";\n" +
                "    if(1 < 0) {\n" +
                "        println \"Won't arrive here\";\n" +
                "    } else if(3 == 3 && !(15 - 2 < 2)) {\n" +
                "        println \"Coolbeans\";\n" +
                "    } else {\n" +
                "        println \"Won't arrive here\";\n" +
                "    }\n" +
                "    println \"Moving on yet again\";\n" +
                "    if(1 < 0) {\n" +
                "        println \"Won't arrive here\";\n" +
                "    } else if(false) {\n" +
                "        println \"Won't arrive here either\";\n" +
                "    } else {\n" +
                "        println \"In the else\";\n" +
                "    }\n" +
                "    println \"Leaving module IfStatements\";\n" +
                "}";
        AST program = parseProgram(programText);
        System.out.println(program);
        System.out.println(program.evaluate());
    }

}
