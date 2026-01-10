package java.parser.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize(){
        List<Token> tokens =new ArrayList<>();

        while (!isAtEnd()){
            char current =  input.charAt(position);

            if(Character.isWhitespace(current)){
               advance();
                continue;
            }

            switch(current){
                case '{':
                    tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                    advance();
                    continue;
                case '}':
                    tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                    advance();
                    continue;
                case '[':
                    tokens.add(new Token(TokenType.LEFT_BRACKET, "["));
                    advance();
                    break;
                case ']':
                    tokens.add(new Token(TokenType.RIGHT_BRACKET, "]"));
                    advance();
                    break;
                case ':':
                    tokens.add(new Token(TokenType.COLON, ":"));
                    advance();
                    break;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ","));
                    advance();
                    break;
                case '"':
                    tokens.add(readString());
                    break;
                default:
                    if(Character.isDigit(current) || (current == '-')){
                        tokens.add(readNumber());
                    } else if (startsWith("true") || startsWith("false")) {
                        tokens.add(readBoolean());
                    } else if (startsWith("null")) {
                        tokens.add(readNull());
                    }else{
                        throw new RuntimeException("Unexpected Char:"+current);
                    }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private boolean isAtEnd() {
        return position >= input.length();
    }

    private char peek() {
        return input.charAt(position);
    }

    private char advance() {
        return input.charAt(position++);
    }

    private boolean startsWith(String value) {
        return input.startsWith(value, position);
    }

    private Token readString() { return null; }
    private Token readNumber() { return null; }
    private Token readBoolean() { return null; }
    private Token readNull() { return null; }
}
