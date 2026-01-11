package parser.lexer;

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
                        error(current);
                    }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private void error(char ch) {
        int start = Math.max(0, position - 10);
        int end = Math.min(input.length(), position + 10);

        String context = input.substring(start, end);

        throw new RuntimeException(
                "Unexpected character '" + ch + "' at position " + position +
                        "\nContext: \"" + context + "\""
        );
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

        while (!isAtEnd()) {
            char current = peek();

            if (current == '"') {
                advance();
                return new Token(TokenType.STRING, value.toString());
            }

            if (current == '\\') {
                advance(); // consume '\'
                char esc = advance();
                switch (esc) {
                    case '"': value.append('"'); break;
                    case '\\': value.append('\\'); break;
                    case 'n': value.append('\n'); break;
                    case 't': value.append('\t'); break;
                    case 'r': value.append('\r'); break;
                    default:
                        throw new RuntimeException("Invalid escape \\" + esc);
                }
            } else {
                value.append(advance());
            }
        }
        throw new RuntimeException("Unterminated string literal");
    }

    private Token readNumber() {
        StringBuilder value = new StringBuilder();

        if (peek() == '-') {
            value.append(advance());
        }

        while (!isAtEnd() && Character.isDigit(peek())) {
            value.append(advance());
        }
        if (!isAtEnd() && peek() == '.') {
            value.append(advance());

            if (isAtEnd() || !Character.isDigit(peek())) {
                throw new RuntimeException("Invalid formate");
            }

            while (!isAtEnd() && Character.isDigit(peek())) {
                value.append(advance());
            }
        }
        return new Token(TokenType.NUMBER, value.toString());
    }

    private Token readBoolean() {
        if(startsWith("true")){
            position+=4;
            return new Token(TokenType.BOOLEAN, "true");
        } else if (startsWith("false")) {
            position+=5;
            return new Token(TokenType.BOOLEAN, "false");
        }
        throw new RuntimeException("Invalid Boolean value");
    }

    private Token readNull() {
        if(startsWith("null")){
            position+=4;
            return new Token(TokenType.NULL, "null");
        }
        throw new RuntimeException("Invalid null value");
    }
}
