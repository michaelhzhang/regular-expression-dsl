package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class StarFactorNode extends InteriorNode {
    public Automaton buildNFA() {
        assert (this.children.size() == 1);
        AutomatonState startState = new AutomatonState();
        AutomatonState endState = new AutomatonState();
        ParseTreeNode child = this.children.get(0);
        Automaton childNFA = child.buildNFA();
        AutomatonState childStart = childNFA.getStart();
        AutomatonState childEnd = childNFA.getOut();
        startState.addEpsilonTransition(childStart);
        childEnd.addEpsilonTransition(startState);
        startState.addEpsilonTransition(endState);
        return new Automaton(startState,endState);
    }
}
