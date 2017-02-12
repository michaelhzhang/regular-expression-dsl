package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class PlusFactorNode extends InteriorNode {

    public Automaton buildNFA() {
        assert (this.children.size() == 1);
        AutomatonState startState = new AutomatonState();
        AutomatonState endState = new AutomatonState();
        ParseTreeNode child = this.children.get(0);
        Automaton childNFA = child.buildNFA();
        AutomatonState childStart = childNFA.getStart();
        AutomatonState childEnd = childNFA.getOut();
        startState.addEpsilonTransition(childStart);
        childEnd.addEpsilonTransition(endState);
        endState.addEpsilonTransition(startState);
        return new Automaton(startState,endState);
    }
}
