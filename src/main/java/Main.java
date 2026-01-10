import parser.lexer.Token;
import parser.lexer.Lexer;
import java.util.List;

public class Main  {
    public static void main(String[] args) {
        String json = "{ \"name\": \"Akshit\", \"age\": 21, \"active\": true, \"skills\": [\"Java\", \"C++\"] }";

        Lexer lexer = new Lexer(json);
        List<Token> tokens = lexer.tokenize();
        for (Token token: tokens){
            System.out.println(token);
        }
    }
}
