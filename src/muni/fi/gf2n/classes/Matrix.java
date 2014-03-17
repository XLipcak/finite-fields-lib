package muni.fi.gf2n.classes;

import java.util.Arrays;
import java.util.Random;

/**
 * Class Matrix is objective representation of matrix. Data are stored in
 * 2-dimensional array and each element of matrix is accessed by its position in
 * matrix (row, column).
 *
 * @author Jakub Lipcak, Masaryk University
 *
 */
public class Matrix {

    private long[][] elements;
    private int rows, columns;

    /**
     * Constructs a Matrix with 0 rows and columns and with no elements.
     */
    public Matrix() {
        rows = 0;
        columns = 0;
        elements = new long[0][0];
    }

    /**
     * Constructs a Matrix of specified size. All elements of this matrix are
     * set to zero.
     *
     * @param rows number of rows
     * @param columns number of columns
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        elements = new long[rows][columns];
    }

    /**
     * Constructs a Matrix. Constructed Matrix is identical with Matrix given as
     * attribute to this constructor.
     *
     * @param matrix Matrix to clone
     */
    public Matrix(Matrix matrix) {
        rows = matrix.getRows();
        columns = matrix.getColumns();

        elements = new long[rows][columns];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                elements[x][y] = matrix.getElement(x, y);
            }
        }
    }

    /**
     * Constructs a Matrix. Constructed Matrix is identical with Matrix
     * represented by 2-dimensional array given as attribute to this
     * constructor.
     *
     * @param matrix Matrix to clone
     */
    public Matrix(long[][] matrix) {
        rows = matrix.length;
        columns = matrix[0].length;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                elements[x][y] = matrix[x][y];
            }
        }
    }

    /**
     * Constructs a Matrix with specified number of rows and columns.
     * Constructed Matrix is filled up with numbers generated randomly between 0
     * and 2^bitSize - 1 .
     *
     * @param rows number of rows
     * @param columns number of columns
     * @param bitSize bit size of values generated randomly
     */
    public Matrix(int rows, int columns, int bitSize) {
        this(rows, columns);
        Random rn = new Random();

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                elements[x][y] = rn.nextInt((int) Math.pow(2, bitSize));
            }
        }
    }

    /**
     * Set element of matrix at specified position to specified value.
     *
     * @param x row position of element
     * @param y column position of element
     * @param value value to be set
     */
    public void setElement(int x, int y, long value) {
        elements[x][y] = value;
    }

    /**
     * Returns element of matrix at specified position.
     *
     * @param x row position of element
     * @param y column position of element
     * @return element at specified position
     */
    public long getElement(int x, int y) {
        return elements[x][y];
    }

    /**
     * Transpose matrix.
     */
    public void transpose() {
        long[][] transposedMatrix = new long[columns][rows];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                transposedMatrix[y][x] = elements[x][y];
            }
        }

        elements = transposedMatrix;
        columns = rows;
        rows = transposedMatrix.length;
    }

    /**
     * Returns number of rows in matrix.
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns number of columns in matrix.
     *
     * @return number of columns
     */
    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object matrix) {
        if (!(matrix instanceof Matrix)) {
            return false;
        }

        if (((Matrix) matrix).getRows() != rows || ((Matrix) matrix).getColumns() != columns) {
            return false;
        }

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (((Matrix) matrix).getElement(x, y) != elements[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Arrays.deepHashCode(this.elements);
        return hash;
    }

    @Override
    public String toString() {
        String result = new String();
        for (int x = 0; x < rows; x++) {
            result += "[ ";
            for (int y = 0; y < columns; y++) {
                if (y == columns - 1) {
                    result += elements[x][y];
                } else {
                    result += elements[x][y] + ", ";
                }
            }
            result += " ]" + "\n";
        }
        return result;
    }
}
