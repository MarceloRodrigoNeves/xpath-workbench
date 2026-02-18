package com.m7sistemas.xpathworkbench.model;

import org.w3c.dom.Node;

public class PositionedNode {
    private final Node node;
    private final int line;
    private final int column;
    private final String displayText;

    public PositionedNode(Node node, int line, int column, String displayText) {
        this.node = node;
        this.line = line;
        this.column = column;
        this.displayText = displayText;
    }

    public Node getNode() {
        return node;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
