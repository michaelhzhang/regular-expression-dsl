package edu.berkeley.eecs.cs164.pa1;

/**
 * This class parses a simple regular expression syntax into an NFA
 */
public class RegexParser {
    /**
     * Rewritten Grammar in EBNF form:
     * expr -> term ('|' term)*
     * term -> factor (factor)*
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
        return null;
    }

}
