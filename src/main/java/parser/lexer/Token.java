package parser.lexer;

public class Token {
    public final TokenType type;
    public final String lexeme;

    public Token(TokenType tokenType, String lexeme) {
        this.type = tokenType;
        this.lexeme = lexeme;
    }

    @Override
    public String toString(){
        return type + "->" + lexeme;
    }
}
