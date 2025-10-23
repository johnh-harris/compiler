import java.util.ArrayList;

import java.util.List;
import java.util.Arrays;

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

class Parser {
    private final List<Token> tokens;
    private int pos = 0;
    private Token token;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        if (!tokens.isEmpty()) {
            this.token = tokens.get(0);
        } else {
            this.token = new Token("EOF", "", 0);
        }
    }

    private void next() {
        pos++;
        if (pos < tokens.size()) {
            token = tokens.get(pos);
        } else {
            token = new Token("EOF", "", 0);
        }
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
                throw new RuntimeException("Unexpected token in nud: " + t.type + " (line " + t.line + ")");
        }
    }

    private Node led(Token t, Node left) {
        switch (t.type) {
            case "PLUS":
                return new Node("Add", t.value, left, parseExpression(10));
            case "MINUS":
                return new Node("Subtract", t.value, left, parseExpression(10));
            case "MULTIPLICATION":
                return new Node("Multiply", t.value, left, parseExpression(20));
            case "DIVISION":
                return new Node("Divide", t.value, left, parseExpression(20));
            default:
                throw new RuntimeException("Unexpected token in led: " + t.type + " (line " + t.line + ")");
        }
    }

    private int lbp(Token t) {
        switch (t.type) {
            case "PLUS":
            case "MINUS":
                return 10;
            case "MULTIPLICATION":
            case "DIVISION":
                return 20;
            default:
                return 0;
        }
    }
}
