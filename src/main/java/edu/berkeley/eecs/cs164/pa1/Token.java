package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 *
 * Represents tokens from string to parse.
 */
class Token {
    public String lexeme;

    public Token(String lexeme) {
        this.lexeme = lexeme;
    }

    public boolean isEscaped() {
        return this.lexeme.charAt(0) == '\\';
    }

    public char getChar() {
        // Apparently you can't use strings in switch statements?
        if (lexeme == "\\n") {
              return '\n';
        } else if (lexeme == "\\t") {
            return '\t';
        } else if (lexeme == "\\|"){
            return '|';
        } else if (lexeme == "\\("){
            return '(';
        } else if (lexeme == "\\)"){
            return ')';
        } else if (lexeme == "\\*"){
            return '*';
        } else if (lexeme == "\\+"){
            return '+';
        } else if (lexeme == "\\?"){
            return '?';
        } else if (lexeme == "\\\\") {
            return '\\';
        } else {
            return lexeme.charAt(0);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Token)) { return false; }
        Token other = (Token) obj;
        return other.lexeme.equals(this.lexeme);
    }

    @Override
    public String toString() {
        return this.lexeme;
    }
}
