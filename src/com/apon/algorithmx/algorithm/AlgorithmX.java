package com.apon.algorithmx.algorithm;

import com.apon.algorithmx.component.IntegerMatrix;
import com.apon.util.ExecutionTimer;

import java.util.*;

public class AlgorithmX {
    private IntegerMatrix matrix;
    private List<List<Integer>> solutions;
    private boolean hasBeenSolved;

    // Variables only needed for execution of the algorithm.
    private Stack<Step<Integer>> steps;
    private List<Integer> partialSolution;
    private int numberOfRowsDeleted;

    public AlgorithmX(IntegerMatrix matrix) {
        this.matrix = matrix;
        solutions = new ArrayList<>();
        hasBeenSolved = false;

        steps = new Stack<>();
        partialSolution = new ArrayList<>();
        numberOfRowsDeleted = 0;
    }

    /**
     * Get all possible solutions using Algorithm X.
     *
     * @return List of all solutions, where each solution is a list of row numbers of the matrix.
     */
    public List<List<Integer>> getAllSolutions() throws Exception {
        if (hasBeenSolved) {
            return solutions;
        }

        ExecutionTimer t = new ExecutionTimer();
        recursiveStep();
        t.end();
        System.out.println("Calculating all solutions took " + t.duration() + " ms");

        return solutions;
    }

    private void recursiveStep() throws Exception {
        // 1. If the matrix A has no columns, the current partial solution is a valid solution; terminate successfully.
        if (matrix.getN() == 0) {
            // Valid solution found!
            // Add a copy to the solutions list, since we will modify the partialSolution.
            solutions.add(new ArrayList<>(partialSolution));
            return;
        }

        // 2. Choose a column c, with the lowest number of 1's in it.
        int j = matrix.getColumnWithLowestSum();

        // 3. Choose a row r such that A_{r, c} = 1.
        // For this algorithm, we just loop over all rows and execute the recursive steps.
        // Note that we can actually loop over matrix.getM(), because all delete-actions get restored inside the same loop.
        for (int i = 0; i < matrix.getM(); i++) {
            if (matrix.getElement(i, j) != 1) {
                continue;
            }

            // 4. Include row r in the partial solution.
            // Note that we delete rows, so we need to correct the i.
            partialSolution.add(i + numberOfRowsDeleted);

            // 5. Delete all the needed rows and columns.
            deleteRowsAndColumns(i);

            // 6. Repeat this algorithm recursively on the reduced matrix A.
            recursiveStep();

            // So if we finished the steps below, we need to revert the delete.
            revertDeleteRowsAndColumns();
        }
    }

    /**
     * Revert the last step in the steps list.
     */
    private void revertDeleteRowsAndColumns() throws Exception {
        Step<Integer> lastStep = steps.pop();

        // We are restoring rows, so we need to lower numberOfRowsDeleted.
        numberOfRowsDeleted -= lastStep.getNumberOfRowsInSubSteps();

        // Restore the matrix.
        lastStep.revertAllSubStepsOnMatrix(matrix);

        // We are undoing the last step, so the partialSolution needs to be corrected.
        partialSolution.remove(partialSolution.size() - 1);
    }

    /**
     * Delete all corresponding rows and columns. Add a step to the steps list so we can revert it later.
     * @param r Choosen row number (starts at 0).
     */
    private void deleteRowsAndColumns(int r) {
        Step<Integer> step = new Step<>();
        steps.push(step);

        // Removing column 'X' changes the positions of the other columns. Therefore we cannot just loop over j from 0
        // to n, removing all the columns. Since if we remove one, our counting shifts and the for-loop doesnt account for that.
        // Thus we store all the to-be-removed row/column numbers in a row and process this later.
        SortedSet<Integer> rowNumbersToBeRemoved = new TreeSet<>(Collections.reverseOrder());
        SortedSet<Integer> columnNumbersToBeRemoved = new TreeSet<>(Collections.reverseOrder());

        // 5.1 For each column j such that A_{r, j} = 1
        for (int j = 0; j < matrix.getN(); j++) {
            if (matrix.getElement(r,j) != 1) {
                continue;
            }

            // 5.2 for each row i such that A_{i, j} = 1
            for (int i = 0; i < matrix.getM(); i++) {
                if (matrix.getElement(i, j) != 1) {
                    continue;
                }

                // 5.3 Delete row i from matrix A.
                rowNumbersToBeRemoved.add(i);
            }

            // 5.4 Delete column j from matrix A.
            columnNumbersToBeRemoved.add(j);
        }

        // Now do the actual removing. Note that the sets are ordered reversely, so this works.
        // Remove the rows.
        for (int i : rowNumbersToBeRemoved) {
            SubStep<Integer> subStep = new SubStep<>(true, matrix.getRow(i), i);
            step.addSubStep(subStep);
            matrix.removeRow(i);
        }
        // Remember the number of rows we delete.
        numberOfRowsDeleted += rowNumbersToBeRemoved.size();

        // Remove the columns.
        for (int j : columnNumbersToBeRemoved) {
            SubStep<Integer> subStep = new SubStep<>(false, matrix.getColumn(j), j);
            step.addSubStep(subStep);
            matrix.removeColumn(j);
        }
    }
}
