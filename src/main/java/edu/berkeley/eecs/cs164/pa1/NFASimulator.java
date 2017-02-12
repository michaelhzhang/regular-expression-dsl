package edu.berkeley.eecs.cs164.pa1;

import java.util.*;

/**
 * This class simulates a non-deterministic finite automaton over ASCII strings.
 */
public class NFASimulator {
    private final Automaton nfa;

    /**
     * Create a new simulator from a given NFA structure
     *
     * @param nfa the nfa to simulate
     */
    public NFASimulator(Automaton nfa) {
        this.nfa = nfa;
    }

    /**
     * Determines whether or not the given text is accepted by the NFA
     *
     * @param text the text to try matching
     * @return true if the text is accepted by the NFA, else false
     */
    public boolean matches(String text) {
        AutomatonState start = this.nfa.getStart();
        AutomatonState end = this.nfa.getOut();
        Set<AutomatonState> currentSet = new HashSet<AutomatonState>();
        currentSet.add(start);
        computeClosure(currentSet);
        for (int i = 0; i < text.length(); i++) {
            char currChar = text.charAt(i);
            currentSet = computeEdge(currentSet, currChar);
            computeClosure(currentSet);
        }
        return currentSet.contains(end);
    }

    private void computeClosure(Set<AutomatonState> visited) {
        Queue<AutomatonState> toVisit = new LinkedList<AutomatonState>();
        toVisit.addAll(visited);
        AutomatonState currState;
        while (toVisit.size() > 0) {
            currState = toVisit.remove();
            if (!visited.contains(currState)) {
                visited.add(currState);
            }
            Set<AutomatonState> epsilonTransitions = currState.getEpsilonTransitions();
            toVisit.addAll(epsilonTransitions);
        }
    }

    private Set<AutomatonState> computeEdge(Set<AutomatonState> visited, char c) {
        Iterator<AutomatonState> existingStates = visited.iterator();
        Set<AutomatonState> result = new HashSet<AutomatonState>();
        while (existingStates.hasNext()) {
            AutomatonState state = existingStates.next();
            Set<AutomatonState> toAdd = state.getTransitions(c);
            result.addAll(toAdd);
        }
        return result;
    }

}
