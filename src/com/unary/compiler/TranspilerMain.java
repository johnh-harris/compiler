
// === Step 3: Example Main (Parser + Transpiler together) ===
public class TranspilerMain {
    public static void main(String[] args) {
        // This assumes you already have a Lexer + Parser
        Lexer lexer = new Lexer("1 + 2 * 3");
        Parser parser = new Parser(lexer);
        Node ast = parser.parseExpression(0);

        // Now transpile to unary instructions
        Transpiler transpiler = new Transpiler();
        String unaryCode = transpiler.transpile(ast);

        System.out.println("=== Unary Code ===");
        System.out.println(unaryCode);
    }
}
