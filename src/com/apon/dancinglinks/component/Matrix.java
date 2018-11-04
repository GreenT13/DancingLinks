package com.apon.dancinglinks.component;

import java.util.List;

public class Matrix {
    private List<ColumnHeader> columnHeaders;
    private List<RowHeader> rowHeaders;

    /**
     * We assume that the whole matrix is already created and stored through columnHeaders.
     * @param columnHeaders Whole matrix basically.
     */
    public Matrix(List<ColumnHeader> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public Matrix(List<ColumnHeader> columnHeaders, List<RowHeader> rowHeaders) {
        this.columnHeaders = columnHeaders;
        this.rowHeaders = rowHeaders;
    }

    public List<ColumnHeader> getColumnHeaders() {
        return columnHeaders;
    }

    public List<RowHeader> getRowHeaders() {
        return rowHeaders;
    }
}
