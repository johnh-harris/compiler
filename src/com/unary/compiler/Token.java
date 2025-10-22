package src.com.unary.compiler;

// Returns output file of tokens

class Token {
    public final String type;
    public final String value;
    public final int line;
    public Token next;

    public Token(String type, String value, int line, Token next) {
        this.type = type;
        this.value = value;
        this.next = next;
        this.line = line;
    }
}
