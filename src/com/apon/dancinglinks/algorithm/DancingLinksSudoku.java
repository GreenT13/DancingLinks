package com.apon.dancinglinks.algorithm;

import com.apon.dancinglinks.component.Matrix;
import com.apon.dancinglinks.component.RowHeader;
import com.apon.util.ExecutionTimer;

import java.util.List;

public class DancingLinksSudoku extends DancingLinks {
    private List<RowHeader> startingSolution;

    public DancingLinksSudoku(Matrix matrix, List<RowHeader> startingSolution) {
        super(matrix);
        this.startingSolution = startingSolution;
    }

    @Override
    public List<List<String>> getAllSolutions() throws Exception {
        ExecutionTimer t = new ExecutionTimer();

        // Remove all rows from the matrix as if several rows have been used.
        for (RowHeader rowHeader: startingSolution) {
            deleteRowsAndColumns(rowHeader);

            // Undo saving to save memory.
            deletionSteps.pop();
        }

        super.getAllSolutions();
        t.end();
        System.out.println("Calculating all sudoku solutions took " + t.duration() + " ms");

        return super.getAllSolutions();
    }


}
