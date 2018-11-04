package com.apon.dancinglinks.component.iterator;

import com.apon.dancinglinks.component.Node;

import java.util.Iterator;

public class DirectionIterator implements Iterator<Node> {
    private Node node;
    private PickCommand pickCommand;
    private boolean useFirstNode;

    public DirectionIterator(Node node, PickCommand pickCommand) {
        this.node = node;
        this.pickCommand = pickCommand;
        useFirstNode = true;
    }

    @Override
    public boolean hasNext() {
        if (useFirstNode) {
            return node != null;
        }

        return (pickCommand.pickDirection(node) != null);
    }

    @Override
    public Node next() {
        if (useFirstNode) {
            useFirstNode = false;
            return node;
        }

        node = pickCommand.pickDirection(node);
        return node;
    }
}
