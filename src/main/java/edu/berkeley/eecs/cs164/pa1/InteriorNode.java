package edu.berkeley.eecs.cs164.pa1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelzhang on 2/11/17.
 */
public abstract class InteriorNode implements ParseTreeNode {

    List<ParseTreeNode> children;
    InteriorNode() {
        this.children = new ArrayList<ParseTreeNode>();
    }
    void addChild(ParseTreeNode node) {
        this.children.add(node);
    }
}
