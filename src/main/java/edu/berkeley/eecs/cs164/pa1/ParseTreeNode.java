package edu.berkeley.eecs.cs164.pa1;

import java.util.LinkedList;

/**
 * Created by michaelzhang on 2/11/17.
 */
public class ParseTreeNode {
    LinkedList children;
    String type;
    boolean isTerminal;

    public ParseTreeNode(String type, boolean isTerminal) {
        this.type = type;
        children = new LinkedList();
        this.isTerminal = isTerminal;
    }

    public void addChild(Object child) {
        children.addLast(child);
    }

    @Override
    public String toString() {
        if (isTerminal) {
            return type;
        }
        return type +
                "(" + children +
                ")";
    }

    public boolean isEmpty() {
        return (this.type.length() == 0);
    }

    public boolean isTerminal() {
        return this.isTerminal;
    }
}
