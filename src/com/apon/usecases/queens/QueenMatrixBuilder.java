package com.apon.usecases.queens;

import com.apon.dancinglinks.builder.MatrixBuilder;
import com.apon.dancinglinks.component.ColumnHeader;
import com.apon.dancinglinks.component.Matrix;

import java.util.ArrayList;
import java.util.List;

public class QueenMatrixBuilder {
    private final int N;
    private MatrixBuilder matrixBuilder;

    public QueenMatrixBuilder(int n) {
        N = n;
        matrixBuilder = new MatrixBuilder(N * N, 6 * N - 2);
    }

    public void createMatrix() {
        for (int i = 0; i < N * N; i++) {
            matrixBuilder.setRow(getColumnNumbersToFill(i));
        }

        setRowIdentifiers();
        setSecondaryHeaders();
    }

    private void setRowIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        for (int i = 0; i < N*N; i++) {
            identifiers.add(convertRankToChar(1 + getRank(i)) + (1 + getFile(i)));
        }

        matrixBuilder.setRowIdentifiers(identifiers.toArray(new String[]{}));
    }

    private void setSecondaryHeaders() {
        List<ColumnHeader> columnHeaders = matrixBuilder.getColumnHeaders();
        for (int i = 16; i < 6 * N - 2; i++) {
            columnHeaders.get(i).setPrimary(false);
        }
    }

    private String convertRankToChar(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 'A' - 1)) : null;
    }

    private int[] getColumnNumbersToFill(int i) {
        int[] result = new int[4];
        result[0] = getRank(i);
        result[1] = getFile(i) + N;
        result[2] = getFile(i) + getRank(i) + 2 * N;
        result[3] = N - 1 - getRank(i) + getFile(i) + 4 * N - 1;

        return result;
    }

    private int getRank(int i) {
        return (i - (i % N)) / N;
    }

    private int getFile(int i) {
        return i % N;
    }

    public Matrix getMatrix() {
        return matrixBuilder.getMatrix();
    }
}
