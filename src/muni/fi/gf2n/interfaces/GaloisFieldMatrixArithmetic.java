/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldMatrixArithmetic {
    long[][] add(long[][] matrix1, long[][] matrix2);
    long[][] subtract(long[][] matrix1, long[][] matrix2);
    long[][] transpose(long[][] matrix);
    
    //matrix*matrix
    long[][] multiply(long[][] matrix1, long[][] matrix2);
    
    //matrix*scalar
    long[][] multiply(long[][] matrix, long scalarValue);
    
    //matrix*vector
    long[][] multiply(long[][] matrix, long[] vector);
    long[][] multiply(long[] vector, long[][] matrix);
    
    long[][] inverse(long[][] matrix);
    long[][] power(long[][] matrix, long exponent);
    long determinant(long[][] matrix);
    int rank(long[][] matrix);
    long[][] gauss(long[][] matrix);

    //returnVector*equationMatrix = results
    long[] solveLinearEquationsSystem(long[][] equationMatrix, long[] results);
    
    long[][] image(long[][] matrix);
    long[][] kernel(long[][] matrix);
    int compare(long[][] matrix1, long[][] matrix2);
}
