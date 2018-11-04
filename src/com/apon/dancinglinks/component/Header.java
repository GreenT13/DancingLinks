package com.apon.dancinglinks.component;

/**
 * Some interface to make RowHeader and ColumnHeader storable in one list.
 */
public abstract class Header implements Comparable<Header> {
    private int identifier;

    private int relativeIdentifier;

    public abstract boolean isColumn();

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public int getRelativeIdentifier() {
        return relativeIdentifier;
    }

    public void setRelativeIdentifier(int relativeIdentifier) {
        this.relativeIdentifier = relativeIdentifier;
    }

    @Override
    public int compareTo(Header header) {
        return this.getIdentifier() - header.getIdentifier();
    }
}
