package edu.berkeley.eecs.cs164.pa1;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class ExpressionNode extends InteriorNode {
    public Automaton buildNFA() {
        AutomatonState startState = new AutomatonState();
        AutomatonState endState = new AutomatonState();
        for (ParseTreeNode child: this.children) {
            Automaton childNFA = child.buildNFA();
            AutomatonState childStart = childNFA.getStart();
            AutomatonState childEnd = childNFA.getOut();
            startState.addEpsilonTransition(childStart);
            childEnd.addEpsilonTransition(endState);
        }
        return new Automaton(startState,endState);
    }
}
