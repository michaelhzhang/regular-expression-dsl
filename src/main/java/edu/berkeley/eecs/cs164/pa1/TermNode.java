package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class TermNode extends InteriorNode {
    public Automaton buildNFA() {
        AutomatonState startState = null;
        AutomatonState endState = null;
        for (ParseTreeNode child: this.children) {
            Automaton childNFA = child.buildNFA();
            if (startState == null) {
                startState = childNFA.getStart();
                endState = childNFA.getOut();
            } else {
                AutomatonState nextState = childNFA.getStart();
                endState.addEpsilonTransition(nextState);
                endState = childNFA.getOut();
            }
        }
        return new Automaton(startState, endState);
    }
}
