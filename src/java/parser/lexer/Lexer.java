package java.parser.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char current = input.charAt(position);

            if (Character.isWhitespace(current)) {
                advance();
                continue;
            }

            switch (current) {
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
                    if (Character.isDigit(current) || (current == '-')) {
                        tokens.add(readNumber());
                    } else if (startsWith("true") || startsWith("false")) {
                        tokens.add(readBoolean());
                    } else if (startsWith("null")) {
                        tokens.add(readNull());
                    } else {
                        throw new RuntimeException("Unexpected Char:" + current);
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

    private Token readString() {
        advance();

        StringBuilder value = new StringBuilder();

        while(!isAtEnd()) {
            char current = advance();

            if (current == '"') {
                return new Token(TokenType.STRING, value.toString());
            }

            if (current == '\\') {
                if (!isAtEnd()) {
                    throw new RuntimeException("Unterminated escape squence");
                }

                char escaped = advance();
                switch (escaped) {
                    case '"':
                        value.append('"');
                        break;
                    case '\\':
                        value.append('\\');
                        break;
                    case '/':
                        value.append('/');
                        break;
                    case 'b':
                        value.append('\b');
                        break;
                    case 'f':
                        value.append('\f');
                        break;
                    case 'n':
                        value.append('\n');
                        break;
                    case 'r':
                        value.append('\r');
                        break;
                    case 't':
                        value.append('\t');
                        break;
                    default:
                        throw new RuntimeException("Invalid escape character: \\" + escaped);
                }
            } else {
                value.append(current);
            }
        }
        throw new RuntimeException("Unterminated string literal");
    }

    private Token readNumber() {
        return null;
    }

    private Token readBoolean() {
        return null;
    }

    private Token readNull() {
        return null;
    }
}
