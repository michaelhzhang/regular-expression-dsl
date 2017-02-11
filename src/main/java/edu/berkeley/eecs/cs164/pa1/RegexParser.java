package edu.berkeley.eecs.cs164.pa1;

import java.util.ArrayList;
import java.util.List;

/**
 * This class parses a simple regular expression syntax into an NFA
 */
public class RegexParser {
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
    private RegexParser() {
    }

    /**
     * This is the main function of this object. It kicks off
     * whatever "compilation" process you write for converting
     * regex strings to NFAs.
     *
     * @param pattern the pattern to compile
     * @return an NFA accepting the pattern
     * @throws RegexParseException upon encountering a parse error
     */
    public static Automaton parse(String pattern) {
        List<Token> tokens = lexPattern(pattern);
        System.out.println(tokens);
        ParseTreeNode parseTree = buildParseTree(tokens);
        System.out.println(parseTree);
        Automaton nfa = buildNFA(parseTree);
        return nfa;
    }

    private static List<Token> lexPattern(String pattern) {
        List<Token> tokens = new ArrayList<Token>();
        Boolean escaped = false;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if ((!escaped) && (isEscapeChar(c))) {
                escaped = true;
            } else {
                Token token;
                if (escaped) {
                    escaped = false;
                    token = new Token("\\" + c);
                } else {
                    token = new Token(Character.toString(c));
                }
                tokens.add(token);
            }
        }
        return tokens;
    }

    private static boolean isEscapeChar(char c) {
        return (c == '\\');
    }

    private static ParseTreeNode buildParseTree(List<Token> tokens) {
        Parser parser = new Parser();
        return parser.buildParseTree(tokens);
    }

    private static Automaton buildNFA(ParseTreeNode parseTree) {
        return null;
        //return combineChildrenNFAs(parseTree.children);
    }



}
