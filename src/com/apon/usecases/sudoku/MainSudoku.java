package com.apon.usecases.sudoku;

import com.apon.dancinglinks.algorithm.DancingLinksSudoku;
import com.apon.dancinglinks.component.Matrix;
import com.apon.dancinglinks.component.RowHeader;

import java.util.ArrayList;
import java.util.List;

public class MainSudoku {

    public static void main(String[] args) throws Exception {
        SudokuMatrixBuilder sudokuMatrixBuilder = new SudokuMatrixBuilder();
        sudokuMatrixBuilder.createMatrix();
        Matrix matrix = sudokuMatrixBuilder.getMatrix();

        String insertSudoku = "4     5 3956  8    2 519 8   74  2 613     752 9  74   4 231 6    7  8516 1     2";
        List<String> rowIdentifiers = new ArrayList<>();
        // Walk through the sudoku from element 0 to 80. Convert the number to RiCj#v notation.
        for (int i = 0; i < 81; i++) {
            if (insertSudoku.toCharArray()[i] == ' ') {
                continue;
            }

            int digit = Integer.valueOf(String.valueOf(insertSudoku.toCharArray()[i]));
            rowIdentifiers.add("R" + ((i - (i % 9)) / 9) + "C" + (i % 9) + "#" + digit);
        }

        // Search all rowHeaders that correspond to the rowIdentifiers.
        List<RowHeader> startingSolution = new ArrayList<>();
        for (RowHeader rowHeader : matrix.getRowHeaders()) {
            if (rowIdentifiers.contains(rowHeader.getRowIdentifier())) {
                startingSolution.add(rowHeader);
            }
        }

        DancingLinksSudoku dancingLinksSudoku = new DancingLinksSudoku(matrix, startingSolution);
        System.out.println(dancingLinksSudoku.getAllSolutions());
    }
}