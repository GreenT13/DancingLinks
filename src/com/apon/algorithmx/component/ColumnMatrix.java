package com.apon.algorithmx.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a matrix, where it is stored as list of lists, where each list represents a column.
 * @param <T>
 */
public class ColumnMatrix<T> {
    protected List<List<T>> matrix;

    // Number of columns.
    private int n;

    // Number of rows.
    private int m;

    /**
     * Construct m x n matrix with 'null' in each position.
     * @param m Number of rows.
     * @param n Number of columns.
     */
    public ColumnMatrix(int m, int n) {
        this(m, n, null);
    }

    /**
     * Construct m x n matrix with defaultValue in each position.
     * @param m Number of rows.
     * @param n Number of columns.
     * @param defaultValue Default value to put in each position.
     */
    public ColumnMatrix(int m, int n, T defaultValue) {
        this.m = m;
        this.n = n;

        matrix = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            ArrayList<T> columnI = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                columnI.add(defaultValue);
            }
            matrix.add(columnI);
        }
    }

    /**
     * Remove entire column from the matrix.
     * @param j Column number (starts at 0).
     * @return The column that is removed.
     */
    public List<T> removeColumn(int j) {
        List<T> column = matrix.get(j);
        matrix.remove(j);

        n--;

        return column;
    }

    /**
     * Remove entire row from the matrix.
     * @param i Row number (starts at 0).
     * @return The row that is removed.
     */
    public List<T> removeRow(int i) {
        List<T> row = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            row.add(matrix.get(j).get(i));
            matrix.get(j).remove(i);
        }

        m--;

        return row;
    }

    /**
     * Add column on certain position in the matrix.
     * @param column List with all values of the column.
     * @param j Column number it will get (starts at 0).
     * @throws Exception Whenever the column length is incorrect or number of columns in the matrix is less than j - 1.
     */
    public void addColumn(List<T> column, int j) throws Exception {
        if (column.size() != m) {
            throw new Exception("Column does not have the right size to be inserted!");
        }
        if (matrix.size() < j - 1) {
            throw new Exception("Matrix is too small to put anything in position j!");
        }

        matrix.add(j, column);
        n++;
    }

    /**
     * Add row on a certain position in the matrix.
     * @param row List with all values of the column.
     * @param i Row number it will get (starts at 0).
     * @throws Exception Whenever the row length is incorrect or number of rows in the matrix is less than i - 1.
     */
    public void addRow(List<T> row, int i) throws Exception {
        if (row.size() != n) {
            throw new Exception("Column does not have the right size to be inserted!");
        }
        if (matrix.size() < i - 1) {
            throw new Exception("Matrix is too small to put anything in position j!");
        }

        for (int j = 0; j < n; j++) {
            matrix.get(j).add(i, row.get(j));
        }
        m++;
    }

    /**
     * Return a full column.
     * @param j Column number (starts at 0).
     */
    public List<T> getColumn(int j) {
        return matrix.get(j);
    }

    /**
     * Return a row.
     * @param i Row number (starts at 0).
     */
    public List<T> getRow(int i) {
        List<T> row = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            row.add(matrix.get(j).get(i));
        }

        return row;
    }

    /**
     * Get the element in position (i,j).
     * @param i Row number (start at 0).
     * @param j Column number (start at 0).
     * @return T
     */
    public T getElement(int i, int j) {
        return matrix.get(j).get(i);
    }

    /**
     * Set a value on position (i,j).
     * @param i Row number (start at 0).
     * @param j Column number (start at 0).
     * @param value Value to place in the matrix.
     */
    public void setElement(int i, int j, T value) {
        matrix.get(j).set(i, value);
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
}
