package com.apon.dancinglinks.algorithm;

import com.apon.dancinglinks.component.*;
import com.apon.util.ExecutionTimer;

import java.util.*;

public class DancingLinks {
    private MatrixOperator matrixOperator;
    private List<List<String>> solutions;
    private boolean hasBeenSolved;

    // Variables only needed for execution of the algorithm.
    private Stack<String> partialSolution;
    protected Stack<Stack<Header>> deletionSteps;

    public DancingLinks(Matrix matrix) {
        matrixOperator = new MatrixOperator(matrix);
        solutions = new ArrayList<>();
        hasBeenSolved = false;

        partialSolution = new Stack<>();
        deletionSteps = new Stack<>();
    }

    /**
     * Get all possible solutions using Dancing Links algorithm.
     *
     * @return List of all solutions, where each solution is a list of row numbers of the matrix.
     */
    @SuppressWarnings("Duplicates")
    public List<List<String>> getAllSolutions() throws Exception {
        if (hasBeenSolved) {
            return solutions;
        }

        // In case this hasn't been done, we just fill it anyway.
        fillAllIdentifiers();

        ExecutionTimer t = new ExecutionTimer();
        recursiveStep();
        hasBeenSolved = true;
        t.end();
        System.out.println("Calculating all solutions took " + t.duration() + " ms");


        return solutions;
    }

    private void fillAllIdentifiers() {
        // Give all headers an identifier based on order in the list.
        int i = 0;
        for (RowHeader rowHeader : matrixOperator.getMatrix().getRowHeaders()) {
            rowHeader.setIdentifier(i);
            i++;
        }

        i = 0;
        for (ColumnHeader columnHeader : matrixOperator.getMatrix().getColumnHeaders()) {
            columnHeader.setIdentifier(i);
            i++;
        }
    }

    private void recursiveStep() throws Exception {
        // 1. If the matrix A has no primary columns, the current partial solution is a valid solution; terminate successfully.
        if (!matrixOperator.hasPrimaryColumns()) {
            // Valid solution found!
            // Add a copy to the solutions list, since we will modify the partialSolution.
            solutions.add(new ArrayList<>(partialSolution));
            return;
        }

        // 2. Choose a column c, with the lowest number of nodes in it.
        // Since we work with primary and secondary constraints, we only look for primary columns.
        ColumnHeader columnHeader = matrixOperator.getPrimaryColumnWithLeastAmountOfNodes();

        // 3. Choose a row r such that A_{r, c} = 1.
        // We just iterate over all the nodes, first node is on the header, rest is going down.
        Node chosenRow = columnHeader.node;
        while (chosenRow != null) {

            // 4. Include row r in the partial solution.
            partialSolution.push(chosenRow.rowHeader.getRowIdentifier());

            // 5. Delete all the needed rows and columns.
            deleteRowsAndColumns(chosenRow.rowHeader);

            // 6. Repeat this algorithm recursively on the reduced matrix A.
            recursiveStep();

            // So if we finished the steps below, we need to revert the delete.
            restoreRowsAndColumns();

            // Select the next node.
            chosenRow = chosenRow.down;
        }
    }

    protected void deleteRowsAndColumns(RowHeader chosenRow) {
        Stack<Header> deletionSubSteps = new Stack<>();
        deletionSteps.push(deletionSubSteps);

        // Removing column 'X' changes the positions of the other columns. Therefore we cannot just loop over j from 0
        // to n, removing all the columns. Since if we remove one, our counting shifts and the for-loop doesnt account for that.
        // Thus we store all the to-be-removed row/column numbers in a row and process this later.
        SortedSet<RowHeader> rowsToBeRemoved = new TreeSet<>(Collections.reverseOrder());
        SortedSet<ColumnHeader> columnsToBeRemoved = new TreeSet<>(Collections.reverseOrder());

        // 5.1 For each column j such that A_{r, j} = 1
        // Translates to: walk through the nodes from columnHeader we can find.
        Iterator<Node> i1 = chosenRow.getIterator();
        while (i1.hasNext()) {
            ColumnHeader columnHeader = i1.next().columnHeader;

            // 5.2 for each row i such that A_{i, j} = 1
            // Translates to: walk through all the nodes in the column.
            Iterator<Node> i2 = columnHeader.getIterator();
            while(i2.hasNext()) {
                RowHeader rowHeader = i2.next().rowHeader;

                // 5.3 Delete row i from matrix A.
                rowsToBeRemoved.add(rowHeader);
            }

            // 5.4 Delete column j from matrix A.
            columnsToBeRemoved.add(columnHeader);
        }

        // Now do the actual removing. Note that the sets are ordered reversely, so this works.
        // Remove the rows.
        for (RowHeader rowHeader : rowsToBeRemoved) {
            matrixOperator.removeRow(rowHeader);
            deletionSubSteps.push(rowHeader);
        }

        for (ColumnHeader columnHeader : columnsToBeRemoved) {
            matrixOperator.removeColumn(columnHeader);
            deletionSubSteps.push(columnHeader);
        }
    }

    private void restoreRowsAndColumns() {
        // Remove the last step from the stack.
        Stack<Header> deletionSubSteps = deletionSteps.pop();

        // Restore the matrix.
        while (!deletionSubSteps.isEmpty()) {
            Header header = deletionSubSteps.pop();
            matrixOperator.restore(header);
        }

        // Remove the last choice in the partialSolution.
        partialSolution.pop();
    }
}
