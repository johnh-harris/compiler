package src.com.unary.compiler;

// Transpiler.java
import java.util.*;

// === Step 1: Abstract Syntax Tree (already have Node class) ===
class Node {
    public final String type;
    public final Object value;
    public final List<Node> children;

    public Node(String type, Object value, Node... children) {
        this.type = type;
        this.value = value;
        this.children = Arrays.asList(children);
    }
}

// === Step 2: Transpiler (Python → Unary) ===
class Transpiler {
    public String transpile(Node ast) {
        StringBuilder out = new StringBuilder();
        emit(ast, out);
        return out.toString();
    }

    private void emit(Node node, StringBuilder out) {
        switch (node.type) {
            case "Number":
                out.append("CONST ").append(node.value).append("\n");
                break;

            case "Add":
                emit(node.children.get(0), out);
                emit(node.children.get(1), out);
                out.append("UNARY_ADD\n");
                break;

            case "Subtract":
                emit(node.children.get(0), out);
                emit(node.children.get(1), out);
                out.append("UNARY_SUB\n");
                break;

            case "Multiply":
                emit(node.children.get(0), out);
                emit(node.children.get(1), out);
                out.append("UNARY_MUL\n");
                break;

            case "Divide":
                emit(node.children.get(0), out);
                emit(node.children.get(1), out);
                out.append("UNARY_DIV\n");
                break;

            default:
                throw new RuntimeException("Unknown node type: " + node.type);
        }
    }
}

