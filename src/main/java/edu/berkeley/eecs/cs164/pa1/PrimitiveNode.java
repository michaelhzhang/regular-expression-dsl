package edu.berkeley.eecs.cs164.pa1;

import java.util.List;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class PrimitiveNode implements ParseTreeNode {
    private List<Token> lexeme;

    public PrimitiveNode(List<Token> combinedLexeme) {
        this.lexeme = combinedLexeme;
    }

    public Automaton buildNFA() {
        AutomatonState startState = new AutomatonState();
        AutomatonState endState = startState;
        for (Token token: this.lexeme) {
            AutomatonState nextState = new AutomatonState();
            endState.addTransition(token.getChar(), nextState);
            endState = nextState;
        }
        return new Automaton(startState, endState);
    }
}
