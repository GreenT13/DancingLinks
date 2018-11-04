package com.apon.dancinglinks.builder;

import com.apon.dancinglinks.component.ColumnHeader;
import com.apon.dancinglinks.component.Matrix;
import com.apon.dancinglinks.component.Node;
import com.apon.dancinglinks.component.RowHeader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MatrixBuilder {
    private List<ColumnHeader> columnHeaders;
    private List<RowHeader> rowHeaders;

    private List<Node> lastHeaderNode;
    private Node lastRowNode;
    private int rowCounter;
    private int columnCounter;

    /**
     * Builder for a m x n matrix.
     * @param m Number of rows.
     * @param n Number of columns.
     */
    public MatrixBuilder(int m, int n) {
        columnHeaders = new ArrayList<>();
        rowHeaders = new ArrayList<>();
        lastHeaderNode = new ArrayList<>();

        for (int j = 0; j < n; j++) {
            columnHeaders.add(new ColumnHeader(true));
            lastHeaderNode.add(null);
        }

        for (int i = 0; i < m; i++) {
            rowHeaders.add(new RowHeader());
        }

        columnCounter = 0;
        rowCounter = 0;
    }

    public MatrixBuilder setRowIdentifiers(String... identifiers) {
        for (int i = 0; i < identifiers.length; i++) {
            rowHeaders.get(i).setRowIdentifier(identifiers[i]);
        }

        return this;
    }

    /**
     * Add 1's at all mentioned columns.
     * @param columns All column numbers for this row, in order!
     */
    public MatrixBuilder setRow(int... columns) {
        columnCounter = 0;
        int previousJ = -1;
        for (int j : columns) {
            if (previousJ > j) {
                return null;
            }

            // Skip the first (j - previousJ - 1) columns.
            columnCounter += (j - previousJ - 1);

            // Fill the current
            fill();

            previousJ = j;
        }

        nextRow();
        return this;
    }

    private void nextRow() {
        columnCounter = 0;
        lastRowNode = null;
        rowCounter++;
    }

    private void fill() {
        if (columnHeaders.size() <= columnCounter) {
            System.out.println("Error filling! Cannot fill this much.");
            return;
        }

        // We are going to fill all details of this node, and all nodes that need to refer to this node.
        Node node = new Node();

        // Fill rowHeader.
        node.rowHeader = rowHeaders.get(rowCounter);
        if (node.rowHeader.node == null) {
            node.rowHeader.node = node;
        }

        // Fill columnHeader.
        node.columnHeader = columnHeaders.get(columnCounter);
        if (node.columnHeader.node == null) {
            node.columnHeader.node = node;
        }
        node.columnHeader.increaseNode();

        // Fill left and right.
        if (lastRowNode != null) {
            lastRowNode.right = node;
            node.left = lastRowNode;
            lastRowNode = node;
        } else {
            lastRowNode = node;
        }

        // Fill up (there is no node under this node).
        if (lastHeaderNode.get(columnCounter) != null) {
            lastHeaderNode.get(columnCounter).down = node;
            node.up = lastHeaderNode.get(columnCounter);
            lastHeaderNode.set(columnCounter, node);
        } else {
            lastHeaderNode.set(columnCounter, node);
        }

        // We added a node, so increase columnCounter.
        columnCounter++;
    }

    public Matrix getMatrix() {
        // Give all headers an identifier based on order in the list.
        int i = 0;
        for (RowHeader rowHeader : rowHeaders) {
            rowHeader.setIdentifier(i);
            i++;
        }

        i = 0;
        for (ColumnHeader columnHeader : columnHeaders) {
            columnHeader.setIdentifier(i);
            i++;
        }

        return new Matrix(columnHeaders, rowHeaders);
    }

    public List<ColumnHeader> getColumnHeaders() {
        return columnHeaders;
    }

    public List<RowHeader> getRowHeaders() {
        return rowHeaders;
    }

    public static void logMatrix(Matrix matrix) {
        List<RowHeader> rowHeaders = matrix.getRowHeaders();
        List<ColumnHeader> columnHeaders = matrix.getColumnHeaders();

        for (RowHeader rowHeader : rowHeaders) {
            int previousI = -1;
            Iterator<Node> rowIterator = rowHeader.getIterator();
            while (rowIterator.hasNext()) {
                Node node = rowIterator.next();
                int headerI = node.columnHeader.getIdentifier();
                if (headerI < 0) {
                    continue;
                }

                // So we just found column headerI and printed at previousI.
                // So we need to print (headerI - previousI - 1) spaces and then a 1.
                for (int i = 0; i < headerI - previousI - 1; i++) {
                    System.out.print(" ");
                }
                System.out.print("X");
                previousI = headerI;
            }

            System.out.println();
        }
    }
}
