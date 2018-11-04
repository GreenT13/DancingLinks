package com.apon.algorithmx.algorithm;

import java.util.List;

public class SubStep<T> {
    // Boolean indication whether this step represents a row (true) or column (false).
    private boolean isRow;

    // List containing all the values of the row or column that was removed.
    private List<T> list;

    // Integer indicating the row or column number.
    private int position;

    public SubStep(boolean isRow, List<T> list, int position) {
        this.isRow = isRow;
        this.list = list;
        this.position = position;
    }

    public boolean isRow() {
        return isRow;
    }

    public List<T> getList() {
        return list;
    }

    public int getPosition() {
        return position;
    }
}
