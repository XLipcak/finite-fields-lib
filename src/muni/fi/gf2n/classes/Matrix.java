/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.classes;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 *
 * Class matrix
 *
 */
public class Matrix {

    private long[][] elements;
    private int rows, columns;

    public Matrix() {
        rows = 0;
        columns = 0;
        elements = new long[0][0];
    }

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
    
    public Matrix(long[][] matrix){
        rows = matrix.length;
        columns = matrix[0].length;
        
        for(int x = 0; x < rows; x++){
            for(int y = 0; y < columns; y++){
                elements[x][y] = matrix[x][y];
            }
        }
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        elements = new long[rows][columns];
    }

    /*
     * Generate random matrix, each element of size 2^bitSize
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

    public void setElement(int x, int y, long value) {
        elements[x][y] = value;
    }

    public long getElement(int x, int y) {
        return elements[x][y];
    }

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

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object matrix) {
        if (!(matrix instanceof Matrix)) {
            return false;
        }
        
        if (((Matrix)matrix).getRows() != rows || ((Matrix)matrix).getColumns() != columns) {
            return false;
        }

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (((Matrix)matrix).getElement(x, y) != elements[x][y]) {
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
