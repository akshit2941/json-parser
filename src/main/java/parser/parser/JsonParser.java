package parser.parser;

import parser.lexer.Token;
import parser.lexer.TokenType;
import parser.model.JsonArray;
import parser.model.JsonBoolean;
import parser.model.JsonNull;
import parser.model.JsonNumber;
import parser.model.JsonObject;
import parser.model.JsonString;
import parser.model.JsonValue;

import java.util.List;

public class JsonParser {
    private final List<Token> tokens;
    private int current=0;

    public JsonParser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public JsonValue parse(){
        JsonValue value = parseValue();
        expect(TokenType.EOF, "Unexpected Token");
        return value;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token expect(TokenType type, String errorMessage){
        if(check(type)) return advance();
        throw new RuntimeException(errorMessage+ " at token: "+peek());
    }

    private JsonValue parseValue(){
        Token token = peek();

        switch (token.type){
            case STRING:
                advance();
                return new JsonString(token.lexeme);
            case NUMBER:
                advance();
                return new JsonNumber(Double.parseDouble(token.lexeme));
            case BOOLEAN:
                advance();
                return new JsonBoolean(Boolean.parseBoolean(token.lexeme));
            case NULL:
                advance();
                return JsonNull.INSTANCE;
            case LEFT_BRACE:
                return parseObject();

            case LEFT_BRACKET:
                return parseArray();
            default:
                throw new RuntimeException("Unexpected token: " + token);
        }
    }

    private JsonObject parseObject(){
        expect(TokenType.LEFT_BRACE, "Expected '{'");

        JsonObject object = new JsonObject();

        //check for empty object
        if (check(TokenType.RIGHT_BRACE)){
            advance();
            return object;
        }

        while(Boolean.TRUE){
            Token keyToken = expect(TokenType.STRING, "Expected String key");

            expect(TokenType.COLON, "Expected ','");

            JsonValue value = parseValue();

            object.put(keyToken.lexeme, value);

            if(check(TokenType.COMMA)){
                advance();
                continue;
            }
            break;
        }

        expect(TokenType.RIGHT_BRACE, "Expected '}' at the end");
        return object;
    }

    private JsonArray parseArray(){
        expect(TokenType.LEFT_BRACKET, "Expected '[' at start of an array");

        JsonArray array = new JsonArray();

        //for ending array at start
        if(check(TokenType.RIGHT_BRACE)){
            advance();
            return array;
        }

        while(Boolean.TRUE){
            JsonValue value = parseValue();
            array.add(value);

            if(check(TokenType.COMMA)){
                advance();
                continue;
            }
            break;
        }
        expect(TokenType.RIGHT_BRACE, "Expected ']' at end of an array");
        return array;
    }
}
