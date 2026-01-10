package java.parser;

import java.parser.lexer.Lexer;
import java.parser.lexer.Token;
import java.util.List;

public class Main  {
    public static void main(String[] args) {
        String json = "{ \"name\": \"Akshit\", \"age\": 21 }";

        Lexer lexer = new Lexer(json);
        List<Token> tokens = lexer.tokenize();
        for (Token token: tokens){
            System.out.println(token);
        }
    }
}