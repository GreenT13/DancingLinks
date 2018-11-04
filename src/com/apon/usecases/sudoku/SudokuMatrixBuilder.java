package com.apon.usecases.sudoku;

import com.apon.dancinglinks.builder.MatrixBuilder;
import com.apon.dancinglinks.component.Matrix;

import java.util.ArrayList;
import java.util.List;

public class SudokuMatrixBuilder {
    private MatrixBuilder matrixBuilder;

    public SudokuMatrixBuilder() {
        // It is a fixed size!
        matrixBuilder = new MatrixBuilder(729, 324);
    }

    public void createMatrix() {
        List<String> rowIdentifiers = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                for (int i = 0; i < 9; i++) {
                    matrixBuilder.setRow(
                            r * 9 + c,
                            81 + r * 9 + i,
                            162 + c * 9 + i,
                            243 + 9*(3*(r/3)+(c/3)) + i);
                    rowIdentifiers.add("R"+r+"C"+c+"#"+(i+1));
                }
            }
        }

        matrixBuilder.setRowIdentifiers(rowIdentifiers.toArray(new String[]{}));
    }

    public Matrix getMatrix() {
        return matrixBuilder.getMatrix();
    }
}
