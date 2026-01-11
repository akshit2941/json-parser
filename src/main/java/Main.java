import parser.lexer.Token;
import parser.lexer.Lexer;
import parser.model.JsonValue;
import parser.parser.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main  {
    public static void main(String[] args) {
//        String json = "{ \"name\": \"TestApp\", \"version\": 1, \"active\": true }";

        Path path = Path.of("data/test.json");
        String json = null;
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Lexer lexer = new Lexer(json);
        List<Token> tokens = lexer.tokenize();

        JsonParser parser = new JsonParser(tokens);
        JsonValue value = parser.parse();

        System.out.println(value);
    }
}
