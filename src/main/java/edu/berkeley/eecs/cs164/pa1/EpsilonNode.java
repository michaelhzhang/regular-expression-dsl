package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class EpsilonNode implements ParseTreeNode {
    public Automaton buildNFA() {
        AutomatonState placeHolderState = new AutomatonState();
        Automaton placeholder = new Automaton(placeHolderState,placeHolderState);
        return placeholder;
    }
}
