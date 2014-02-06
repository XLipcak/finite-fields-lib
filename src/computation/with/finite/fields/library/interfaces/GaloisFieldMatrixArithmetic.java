/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package computation.with.finite.fields.library.interfaces;

import java.util.List;

/**
 *
 * @author Jakub
 */
public interface GaloisFieldMatrixArithmetic {
    List<List<Long>> add(List<List<Long>> matrix1, List<List<Long>> matrix2);
    List<List<Long>> subtract(List<List<Long>> matrix1, List<List<Long>> matrix2);
    List<List<Long>> transpose(List<List<Long>> matrix);
    
    //matrix*matrix
    List<List<Long>> multiply(List<List<Long>> matrix1, List<List<Long>> matrix2);
    
    //matrix*scalar
    List<List<Long>> multiply(List<List<Long>> matrix, long scalarValue);
    
    //matrix*vector
    List<List<Long>> multiplyByVector(List<List<Long>> matrix, List<Long> vector);
    
    List<List<Long>> inverse(List<List<Long>> matrix);
    List<List<Long>> power(List<List<Long>> matrix, long exponent);
    long determinant(List<List<Long>> matrix);
    long rank(List<List<Long>> matrix);
    List<List<Long>> gauss(List<List<Long>> matrix);

    //returnVector*equationMatrix = results
    List<Long> solveLinearEquationsSystem(List<List<Long>> equationMatrix, List<Long> results);
    
    List<Long> image(List<List<Long>> matrix);
    List<Long> kernel(List<List<Long>> matrix);
    int compare(List<List<Long>> matrix1, List<List<Long>> matrix2);
}
