import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;


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

    // TODO: I know I'm missing something
    public ArrayList<Token> tokenize() {
	ArrayList<Token> tokens = null;
	try (BufferedReader reader = new BufferedReader(new FileReader(this.input))) {
    		tokens = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null) {
        		System.out.println(line);
			boolean isNumber = false;
			boolean isLetter = false;
			String token = "";
			for(int i = 0; i < line.length(); i++) {
				Character c = line.charAt(i);
				if(Character.isDigit(c)) {
					if(isLetter) {
						Token t = new Token("OPERATOR", token, pos);
						tokens.add(t);
						isLetter = false;
						isNumber = true;
						token = "";
					} else {
						token += c;
						isNumber = true;
					}
				} else if(Character.isLetter(c)) {
					if(isNumber) {
						Token t = new Token("NUMBER", token, pos);
						tokens.add(t);
						isLetter = true;
						isNumber = false;
						token = "";
					} else {
						token += c;
						isLetter = true;
					}
				}
			}
			if (!token.isEmpty()) {
    				if (isNumber)
        				tokens.add(new Token("NUMBER", token, pos));
    				else if (isLetter)
        				tokens.add(new Token("OPERATOR", token, pos));
			}
			advance();
			token = "";
    		}
	} catch(Exception e) {
		System.err.println("UNABLE TO READ FILE");
	}
	return tokens;
    }

    // This is probably going to be deprecated
    public Token nextToken(String line) {
	
	// read from file
        // Skip whitespace
        /*while (Character.isWhitespace(peek())) advance();

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
            return new Token("NUMBER", num.toString(), 0);
        }

        // Operators
        switch (ch) {
            case '+': advance(); return new Token("PLUS", "+", 0);
            case '-': advance(); return new Token("MINUS", "-", 0);
            case '*': advance(); return new Token("MULT", "*", 0);
            case '/': advance(); return new Token("DIV", "/", 0);
            default:
                throw new RuntimeException("Unexpected character: " + ch);
        }*/
	return null;
    }
}

