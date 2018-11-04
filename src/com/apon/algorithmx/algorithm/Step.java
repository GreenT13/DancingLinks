package com.apon.algorithmx.algorithm;

import com.apon.algorithmx.component.ColumnMatrix;

import java.util.Stack;

public class Step<T> {
    private Stack<SubStep<T>> subSteps;

    public Step() {
        subSteps = new Stack<>();
    }

    public void addSubStep(SubStep<T> subStep) {
        subSteps.push(subStep);
    }

    public void revertAllSubStepsOnMatrix(ColumnMatrix<T> matrix) throws Exception {
        // Since it is a LinkedList, the first element that is added will be first in this for-loop.
        while (!subSteps.isEmpty()) {
            SubStep<T> subStep = subSteps.pop();
            if (subStep.isRow()) {
                matrix.addRow(subStep.getList(), subStep.getPosition());
            } else {
                matrix.addColumn(subStep.getList(), subStep.getPosition());
            }
        }
    }

    public int getNumberOfRowsInSubSteps() {
        return subSteps.stream().mapToInt(x -> x.isRow() ? 1 : 0).sum();
    }
}
