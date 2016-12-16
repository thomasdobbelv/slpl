package slpl;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.err.TypeError;
import slpl.syntax.ProgramParser;
import slpl.util.Environment;
import slpl.util.Memory;

import java.io.*;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException, TypeError {
        String path = "samples/basic.slpl";
        String programText = load(path);
        AST program = ProgramParser.parseProgram(programText);
        program.checkType(new Environment());
        program.evaluate(new Environment(), new Memory());
    }

    private static String load(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(path)));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

}
