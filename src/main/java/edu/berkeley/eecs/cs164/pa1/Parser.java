package edu.berkeley.eecs.cs164.pa1;

import java.util.ArrayList;
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
            return null;
        }
    }

    private void advance() {
        currToken = getToken();
    }

    private void match(Token t) {
        if ((currToken != null) && (currToken.equals(t))) {
            advance();
        } else {
            if (currToken != null) {
                throw new RegexParseException("Unexpected token: " + currToken
                        + ". Expecting: " + t + ".");
            } else {
                throw new RegexParseException("Null token. Expecting: " + t + ".");
            }
        }
    }

    public ParseTreeNode buildParseTree(List<Token> tokens) {
        this.pos = 0;
        this.input = tokens;
        advance();
        return expr();
    }

    public boolean isEpsilon(ParseTreeNode node) {
        return (node instanceof EpsilonNode);
    }

    private ParseTreeNode expr() {
        ParseTreeNode result = term();
        if (isEpsilon(result)) {
            return result;
        }
        ParseTreeNode toOrWith;
        while ((currToken != null) && (currToken.equals(OR))) {
            advance();
            toOrWith = term();
            ExpressionNode expressionNode = new ExpressionNode();
            expressionNode.addChild(result);
            expressionNode.addChild(toOrWith);
            result = expressionNode;
        }
        return result;
    }

    private ParseTreeNode term() {
        ParseTreeNode result = factor();
        if (!isEpsilon(result)) {
            ParseTreeNode toConcat;
            while ((currToken != null) && (!currToken.equals(OR))) {
                toConcat = factor();
                if (isEpsilon(toConcat)) {
                    break;
                }
                TermNode termNode = new TermNode();
                termNode.addChild(result);
                termNode.addChild(toConcat);
                result = termNode;
            }
        }
        return result;
    }

    private boolean isOperator(Token token) {
        return ((token.equals(PLUS)) || (token.equals(STAR)) || (token.equals(QUESTION)));
    }

    private ParseTreeNode factor() {
        ParseTreeNode result = atom();
        if ((!(result instanceof EpsilonNode)) && (currToken != null) && (isOperator(currToken))) {
            InteriorNode factorNode = null;
            if (currToken.equals(PLUS)) {
                factorNode = new PlusFactorNode();
            } else if (currToken.equals(STAR)) {
                factorNode = new StarFactorNode();
            } else if (currToken.equals(QUESTION)) {
                factorNode = new QuestionFactorNode();
            }
            factorNode.addChild(result);
            result = factorNode;
            advance();
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

    private boolean isPrimitive(Token token) {
        return (token.isEscaped() || isNotSpecial(token));
    }

    private ParseTreeNode atom() {
        if (currToken == null) {
            return new EpsilonNode();
        } else if (currToken.equals(LEFTPAREN)) {
            advance();
            AtomNode atomNode = new AtomNode();
            atomNode.addChild(expr());
            match(RIGHTPAREN);
            return atomNode;
        } else if (isPrimitive(currToken)) {
            List<Token> combinedLexeme = new ArrayList<Token>();
            combinedLexeme.add(currToken);
            advance();
            while ((currToken != null) &&  (isPrimitive((currToken)))) {
                combinedLexeme.add(currToken);
                advance();
            }
            AtomNode atomNode = new AtomNode();
            atomNode.addChild(new PrimitiveNode(combinedLexeme));
            return atomNode;
        } else {
            return new EpsilonNode();
        }
    }

}
