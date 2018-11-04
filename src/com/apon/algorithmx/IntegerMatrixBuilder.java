package com.apon.algorithmx;

import com.apon.algorithmx.component.IntegerMatrix;

public class IntegerMatrixBuilder {
    private IntegerMatrix matrix;
    private int i;

    public IntegerMatrixBuilder(int m, int n) {
        matrix = new IntegerMatrix(m, n);
        i = 0;
    }

    public IntegerMatrixBuilder set(int... columns) {
        for (int j : columns) {
            matrix.setElement(i, j, 1);
        }
        i++;
        return this;
    }

    public IntegerMatrix getMatrix() {
        return matrix;
    }
}
