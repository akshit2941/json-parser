import parser.lexer.Token;
import parser.lexer.Lexer;
import parser.model.JsonValue;
import parser.parser.JsonParser;

import java.util.List;

public class Main  {
    public static void main(String[] args) {
        String json = "{ \"name\": \"TestApp\", \"version\": 1, \"active\": true }";

        Lexer lexer = new Lexer(json);
        List<Token> tokens = lexer.tokenize();

        JsonParser parser = new JsonParser(tokens);
        JsonValue value = parser.parse();

        System.out.println(value);
    }
}
