package com.apon.dancinglinks.component;

import com.apon.dancinglinks.component.iterator.DirectionIterator;
import com.apon.dancinglinks.component.iterator.PickRight;

import java.util.Iterator;

public class RowHeader extends Header {
    // First node in the row.
    public Node node;

    private String rowIdentifier;

    public Iterator<Node> getIterator() {
        return new DirectionIterator(node, new PickRight());
    }

    @Override
    public boolean isColumn() {
        return false;
    }

    public String getRowIdentifier() {
        return rowIdentifier;
    }

    public void setRowIdentifier(String rowIdentifier) {
        this.rowIdentifier = rowIdentifier;
    }
}
