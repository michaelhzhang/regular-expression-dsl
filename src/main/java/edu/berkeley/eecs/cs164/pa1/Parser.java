package edu.berkeley.eecs.cs164.pa1;

import java.util.List;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class Parser {
    /**
     * Rewritten Grammar in EBNF form:
     * expr -> term ('|' term)*
     * term -> factor (factor)* | epsilon
     * factor -> atom ('+' | '*' | '?')?
     * atom -> '\n' | '\t' | '\|' # escapes
     *         | '\(' | '\)' | '\*'
     *         | '\+' | '\?' | '\\'
     *         | '(' expr ') # nested expressions
     *         | any character other than | ( ) * + ? \ # single characters
     */
    private int pos;
    List<Token> input;
    Token currToken;

    private static final Token OR = new Token("|");
    private static final Token PLUS = new Token("+");
    private static final Token STAR = new Token("*");
    private static final Token QUESTION = new Token("?");
    private static final Token LEFTPAREN = new Token("(");
    private static final Token RIGHTPAREN = new Token(")");
    private static final Token BACKSLASH = new Token("\\");
    private static final Token[] SPECIALS = {OR, PLUS, STAR, QUESTION, LEFTPAREN, RIGHTPAREN, BACKSLASH};

    private Token getToken() {
        if (pos < input.size()) {
            return input.get(pos++);
        } else {
            return null; // TODO: Bad style but it's in the example code
        }
    }

    private void advance() {
        currToken = getToken();
    }

    private void match(Token t) {
        if (currToken.equals(t)) {
            advance();
        } else {
            throw new RegexParseException("Unexpected token: " + currToken
                    + ". Expecting: " + t + ".");
        }
    }

    public ParseTreeNode buildParseTree(List<Token> tokens) {
        this.pos = 0;
        this.input = tokens;
        advance();
        return expr();
    }

    private ParseTreeNode expr() { // TODO: rename variables
        ParseTreeNode tmp1 = term();
        ParseTreeNode tmp2;
        while ((currToken != null) && (currToken.equals(OR))) {
            advance();
            tmp2 = term();
            ParseTreeNode tmp = new ParseTreeNode("expr", false);
            tmp.addChild(tmp1);
            tmp.addChild(new ParseTreeNode("|", true));
            tmp.addChild(tmp2);
            tmp1 = tmp;
        }
        return tmp1;
    }

    private ParseTreeNode term() { // TODO: HOW TO DEAL WITH EMPTY TERMS?
        ParseTreeNode tmp1 = factor();
        if (!tmp1.isEmpty()) {
            ParseTreeNode tmp2;
            while ((currToken != null) && (!currToken.equals(OR))) {
                tmp2 = factor();
                if (tmp2.isEmpty()) {
                    break;
                }
                ParseTreeNode tmp = new ParseTreeNode("term", false);
                tmp.addChild(tmp1);
                tmp.addChild(tmp2);
                tmp1 = tmp;
            }
        }
        return tmp1;
    }

    private boolean isOperator(Token token) {
        return ((token.equals(PLUS)) || (token.equals(STAR)) || (token.equals(QUESTION)));
    }

    private ParseTreeNode factor() {
        ParseTreeNode result = atom();
        if ((!result.isEmpty()) && (currToken != null) && (isOperator(currToken))) {
            ParseTreeNode operator = null;
            if (currToken.equals(PLUS)) {
                operator = new ParseTreeNode("+", true);
            } else if (currToken.equals(STAR)) {
                operator = new ParseTreeNode("*", true);
            } else if (currToken.equals(QUESTION)) {
                operator = new ParseTreeNode("?", true);
            }
            ParseTreeNode tmp =  new ParseTreeNode("factor", false);
            tmp.addChild(result);
            tmp.addChild(operator);
            result = tmp;
        }
        return result;
    }

    private boolean isNotSpecial(Token token) {
        boolean notSpecial = true;
        for (int i = 0; i < SPECIALS.length; i++) {
            if (token.equals(SPECIALS[i])) {
                notSpecial = false;
            }
        }
        return notSpecial;
    }

    private ParseTreeNode atom() {
        if (currToken.equals(LEFTPAREN)) {
            advance();
            ParseTreeNode tmp = new ParseTreeNode("atom", false);
            tmp.addChild(expr());
            match(RIGHTPAREN);
            return tmp;
        } else if (currToken.isEscaped() || isNotSpecial((currToken))) {
            String combinedLexeme = currToken.lexeme;
            advance();
            while ((currToken != null) &&
                    (currToken.isEscaped() || isNotSpecial(currToken))) {
                combinedLexeme += currToken.lexeme;
                advance();
            }
            ParseTreeNode tmp = new ParseTreeNode("atom", false);
            tmp.addChild(new ParseTreeNode(combinedLexeme, true));
            return tmp;
        } else {
            return new ParseTreeNode("", true); // Epsilon, Indicates can't parse anything
        }
    }

}
