
class Lexer {
    private final String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input;
    }

    private char peek() {
        if (pos >= input.length()) return '\0';
        return input.charAt(pos);
    }

    private void advance() {
        pos++;
    }

    public Token nextToken() {

	// read from file
        // Skip whitespace
        while (Character.isWhitespace(peek())) advance();

        // End of input
        if (peek() == '\0') return null;

        char ch = peek();

        // Numbers
        if (Character.isDigit(ch)) {
            StringBuilder num = new StringBuilder();
            while (Character.isDigit(peek()) || peek() == '.') {
                num.append(peek());
                advance();
            }
            return new Token("NUMBER", num.toString(), 0, null);
        }

        // Operators
        switch (ch) {
            case '+': advance(); return new Token("PLUS", "+", 0, null);
            case '-': advance(); return new Token("MINUS", "-", 0, null);
            case '*': advance(); return new Token("MULT", "*", 0, null);
            case '/': advance(); return new Token("DIV", "/", 0, null);
            default:
                throw new RuntimeException("Unexpected character: " + ch);
        }
    }
}

