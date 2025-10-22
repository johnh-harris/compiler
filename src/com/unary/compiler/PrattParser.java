package src.com.unary.compiler;

public class PrattParser {
    public static void main(String[] args) {
        // grab tokens this will be a file path
        Lexer lexer = new Lexer("ADD 1 3");
        Parser parser = new Parser(lexer);
        Node result = parser.parseExpression(0);
        System.out.println("Parsed: " + result);
    }
}
