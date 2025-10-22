import java.util.List;
import java.util.Arrays;

class Parser {
    private final Lexer lexer;
    private Token token;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.token = lexer.nextToken();
    }

    private void next() {
        token = lexer.nextToken();
    }

    public Node parseExpression(int rbp) {
        Token t = token;
        next();
        Node left = nud(t);
        while (rbp < lbp(token)) {
            t = token;
            next();
            left = led(t, left);
        }
        return left;
    }

    private Node nud(Token t) {
        if (t.type.equals("NUMBER"))
            return new Node("Number", t.value);
        if (t.type.equals("MINUS"))
            return new Node("Negate", t.value, parseExpression(100)); // high binding power
        
        throw new RuntimeException("Unexpected token in nud: " + t.type);
    }

    private Node led(Token t, Node left) {
        switch (t.type) {
            case "PLUS":
                return new Node("Add", t.value, left, parseExpression(10));
            case "MINUS":
                return new Node("Subtract", t.value, left, parseExpression(10));
            case "MULT":
                return new Node("Multiply", t.value, left, parseExpression(20));
            case "DIV":
                return new Node("Divide", t.value, left, parseExpression(20));
            default:
                throw new RuntimeException("Unexpected token in led: " + t.type);
        }
    }

    private int lbp(Token t) {
        // Binding power table
	if(t == null) return 0; 
        /*
         *   * and / have higher binding power (they grab their operands more tightly). 
         *   + and - have lower binding power (they combine later).
         */
        // setup for math expressions only, but can work with other options
        // for higher priority
        if(t.type.equals("MULTIPLICATION") || t.type.equals("DIVISION")) {
            	return 20;
        } else if(t.type.equals("ADDITION") || t.type.equals("SUBTRACTION")) {
		return 10;
	}
        return 0;
    }
}

class Node {
    public final String type;
    public final Object value;
    public final List<Node> children;

    public Node(String type, Object value, Node... children) {
        this.type = type;
        this.value = value;
        this.children = Arrays.asList(children);
    }

    @Override
    public String toString() {
        if (children.isEmpty()) return value.toString();
        return "(" + type + " " + children + ")";
    }
}
