import java.util.ArrayList;

import java.util.List;
import java.util.Arrays;


class Parser {
	private final Lexer lexer;
	List<Token> tokens = new ArrayList<>();
    	private Token token;

	// this is going to change to an arraylist of tokens
    	public Parser(Lexer lexer) {
        	this.lexer = lexer;
        	//this.token = lexer.nextToken();
    	}

    	private void next() {
        	//token = lexer.nextToken();
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
        	switch (t.type) {
            		case "NUMBER":
                		return new Node("Number", t.value);
            		case "MINUS":
                		return new Node("Negate", t.value, parseExpression(100));
            		default:
                		throw new RuntimeException("Unexpected token in nud: " + t.type);
        	}
    	}

    	private Node led(Token t, Node left) {
        	switch (t.type) {
            		case "PLUS":
                		return new Node("Add", t.value, left, parseExpression(10));
            		case "MINUS":
                		return new Node("Subtract", t.value, left, parseExpression(10));
            		default:
                		throw new RuntimeException("Unexpected token in led: " + t.type);
        	}
    	}

    	private int lbp(Token t) {
        	switch (t.type) {
            		case "PLUS": case "MINUS": return 10;
            		default: return 0;
        	}
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
