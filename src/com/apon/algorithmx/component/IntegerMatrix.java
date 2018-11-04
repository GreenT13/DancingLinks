package com.apon.algorithmx.component;

public class IntegerMatrix extends ColumnMatrix<Integer> {

    /**
     * Construct m x n matrix with 'null' in each position.
     *
     * @param m Number of rows.
     * @param n Number of columns.
     */
    public IntegerMatrix(int m, int n) {
        super(m, n, 0);
    }

    /**
     * Get the sum of all integers in a column.
     * @param j Column number
     * @return int
     */
    public int getSumOfColum(int j) {
        return matrix.get(j).stream().reduce(0, Integer::sum);
    }

    /**
     * Get the sum of all the integers in a row.
     * @param i Row number
     * @return int
     */
    public int getSumOfRow(int i) {
        return matrix.stream().mapToInt(x -> x.get(i)).sum();
    }

    /**
     * Get the column with the lowest sum. Assumes the matrix has at least one column.
     * @return Column number.
     */
    public int getColumnWithLowestSum() {
        int lowestSum = getSumOfColum(0);
        int column = 0;

        for (int j = 1; j < getN(); j++) {
            int sum = getSumOfColum(j);
            if (sum < lowestSum) {
                lowestSum = sum;
                column = j;
            }
        }

        return column;
    }
}
