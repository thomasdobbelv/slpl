package slpl;

import slpl.ast.AST;
import slpl.err.ParseException;
import slpl.err.TypeError;
import slpl.syntax.ProgramParser;
import slpl.util.Environment;
import slpl.util.Memory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Interpreter {

    public static void main(String[] args) throws IOException, ParseException, TypeError {
        String path = "samples/scope.slpl";
        String programText = load(path);
        AST program = ProgramParser.parseProgram(programText);
        program.checkType(new Environment(null));
        program.evaluate(new Environment(null), new Memory());
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
