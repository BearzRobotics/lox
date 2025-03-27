// My guess is a Java package is a continerized directory structure for the code
package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// entry point of our jlox interpreter
public class Lox {
    static boolean hadError = false;
    public static void main(String [] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            // exit codes as defined in the unix sysexits.h
            System.exit(64);
        } else if (args.length == 1) {
            // this confuses me. Normally the first arg to a program is the 
            // program name. In this case jlox. So Shouldn't this be args[1]
            // for the file passed in?
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        // creating an array and getting the path 
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // indicate an error had happed and exit
        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        // Were getting an input 
        InputStreamReader input = new InputStreamReader(System.in);
        // Were adding it to some sort of buffer
        BufferedReader reader = new BufferedReader(input);

        // What the fuck is ;;
        for (;;) {
            // only prints after a command is ran.
            // e.g
            // print "hello";
            // > hello
            System.out.println("> ");

            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

    // code to run stff
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        
        // Is a list the single version of a double linked list?
        // null -> [data| pointer next] -> [data| pointer next] -> null
        List<Token> tokens = scanner.scanTokens();

        // For now lets just print the tokens
        for (Token token : tokens) {
            System.out.println(token);
        } 
    }

    // This has to be a bug. This either needs to be public or private
    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }


}


