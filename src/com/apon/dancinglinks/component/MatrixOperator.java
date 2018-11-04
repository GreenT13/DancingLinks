package com.apon.dancinglinks.component;

import java.util.Iterator;
import java.util.Stack;

/**
 * Class with all matrix functionality that is needed.
 */
public class MatrixOperator {
    private Matrix matrix;
    private Stack<Stack<Node>> steps;

    public Matrix getMatrix() {
        return matrix;
    }

    public MatrixOperator(Matrix matrix) {
        this.matrix = matrix;
        steps = new Stack<>();
    }

    /**
     * Check whether the matrix has any primary columns left.
     */
    public boolean hasPrimaryColumns() {
        for (ColumnHeader columnHeader : matrix.getColumnHeaders()) {
            if (columnHeader.isPrimary()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return the primary columnHeader with the least amount of nodes.
     */
    public ColumnHeader getPrimaryColumnWithLeastAmountOfNodes() {
        ColumnHeader lowestColumnHeader = null;

        for (ColumnHeader columnHeader : matrix.getColumnHeaders()) {
            if (!columnHeader.isPrimary()) {
                continue;
            }

            if (lowestColumnHeader == null || columnHeader.getNrOfNodesInColumn() < lowestColumnHeader.getNrOfNodesInColumn()) {
                lowestColumnHeader = columnHeader;
            }
        }

        return lowestColumnHeader;
    }

    /**
     * Remove a node from it's column.
     * @param node Node to remove.
     */
    private void removeNodeFromColumn(Node node) {
        // If it is the highest node, the columnHeader should now refer to the next node.
        if (node.up == null) {
            node.columnHeader.node = node.down;
        } else {
            node.up.down = node.down;
        }

        if (node.down != null) {
            node.down.up = node.up;
        }

        node.columnHeader.decreaseNode();
    }

    /**
     * Remove a row from the matrix.
     * @param rowHeader Header of the row.
     */
    public void removeRow(RowHeader rowHeader) {
        // To remove a row, you need to remove the nodes from their corresponding columns.
        Iterator<Node> rowIterator = rowHeader.getIterator();
        while (rowIterator.hasNext()) {
            Node node = rowIterator.next();
            removeNodeFromColumn(node);
        }
    }

    /**
     * Remove a node from it's row.
     * @param node Node to remove.
     */
    private void removeNodeFromRow(Node node) {
        // If it is the most-left node, the rowHeader should now refer to the next node.
        if (node.left == null) {
            node.rowHeader.node = node.right;
        } else {
            node.left.right = node.right;
        }

        if (node.right != null) {
            node.right.left = node.left;
        }
    }

    /**
     * Remove a column from the matrix.
     * @param columnHeader Header of the column.
     */
    public void removeColumn(ColumnHeader columnHeader) {
        // To remove a column, you need to remove the nodes from their corresponding rows.
        Node iteratorNode = columnHeader.node;
        while (iteratorNode != null) {
            removeNodeFromRow(iteratorNode);
            iteratorNode = iteratorNode.down;
        }

        columnHeader.setRelativeIdentifier(matrix.getColumnHeaders().indexOf(columnHeader));
        matrix.getColumnHeaders().remove(columnHeader);
    }

    private void restoreNodeInColumn(Node node) {
        // If the node was the highest node, the columnHeader should now refer to this node instead.
        if (node.up == null) {
            node.columnHeader.node = node;
        } else {
            node.up.down = node;
        }

        if (node.down != null) {
            node.down.up = node;
        }
    }

    public void restoreRow(RowHeader rowHeader) {
        // To restore a row, you need to restore the nodes in their corresponding columns.
        Node iteratorNode = rowHeader.node;
        while (iteratorNode != null) {
            restoreNodeInColumn(iteratorNode);
            iteratorNode = iteratorNode.right;
        }
    }

    private void restoreNodeInRow(Node node) {
        // If the node was the most-left node, the rowHeader should now refer to this node instead.
        if (node.left == null) {
            node.columnHeader.node = node;
        } else {
            node.left.right = node;
        }

        if (node.right != null) {
            node.right.left = node;
        }
    }

    public void restoreColumn(ColumnHeader columnHeader) {
        // To restore a row, you need to restore the nodes in their corresponding columns.
        Node iteratorNode = columnHeader.node;
        while (iteratorNode != null) {
            restoreNodeInRow(iteratorNode);
            iteratorNode = iteratorNode.down;
        }

        matrix.getColumnHeaders().add(columnHeader.getRelativeIdentifier(), columnHeader);
    }

    public void restore(Header header) {
        if (header.isColumn()) {
            restoreColumn((ColumnHeader) header);
        } else {
            restoreRow((RowHeader) header);
        }
    }
}
