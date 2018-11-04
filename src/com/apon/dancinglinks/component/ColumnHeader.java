package com.apon.dancinglinks.component;

import com.apon.dancinglinks.component.iterator.DirectionIterator;
import com.apon.dancinglinks.component.iterator.PickDown;

import java.util.Iterator;

public class ColumnHeader extends Header {
    // First node in the column.
    public Node node;

    private int nrOfNodesInColumn;

    private boolean isPrimary;

    public ColumnHeader(boolean isPrimary) {
        this.isPrimary = isPrimary;
        nrOfNodesInColumn = 0;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public int getNrOfNodesInColumn() {
        return nrOfNodesInColumn;
    }

    public void setNrOfNodesInColumn(int nrOfNodesInColumn) {
        this.nrOfNodesInColumn = nrOfNodesInColumn;
    }

    public Iterator<Node> getIterator() {
        return new DirectionIterator(node, new PickDown());
    }

    public void increaseNode() {
        nrOfNodesInColumn++;
    }

    public void decreaseNode() {
        nrOfNodesInColumn--;
    }

    @Override
    public boolean isColumn() {
        return true;
    }
}
