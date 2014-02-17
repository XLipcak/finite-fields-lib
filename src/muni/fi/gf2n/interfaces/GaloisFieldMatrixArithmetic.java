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
    ArrayList<ArrayList<Long>> add(List<List<Long>> matrix1, List<List<Long>> matrix2);
    ArrayList<ArrayList<Long>> subtract(List<List<Long>> matrix1, List<List<Long>> matrix2);
    ArrayList<ArrayList<Long>> transpose(List<List<Long>> matrix);
    
    //matrix*matrix
    ArrayList<ArrayList<Long>> multiply(List<List<Long>> matrix1, List<List<Long>> matrix2);
    
    //matrix*scalar
    ArrayList<ArrayList<Long>> multiply(List<List<Long>> matrix, long scalarValue);
    
    //matrix*vector
    ArrayList<ArrayList<Long>> multiplyByVector(List<List<Long>> matrix, List<Long> vector);
    
    ArrayList<ArrayList<Long>> inverse(List<List<Long>> matrix);
    ArrayList<ArrayList<Long>> power(List<List<Long>> matrix, long exponent);
    long determinant(List<List<Long>> matrix);
    long rank(List<List<Long>> matrix);
    ArrayList<ArrayList<Long>> gauss(List<List<Long>> matrix);

    //returnVector*equationMatrix = results
    ArrayList<Long> solveLinearEquationsSystem(List<List<Long>> equationMatrix, List<Long> results);
    
    ArrayList<Long> image(List<List<Long>> matrix);
    ArrayList<Long> kernel(List<List<Long>> matrix);
    int compare(List<List<Long>> matrix1, List<List<Long>> matrix2);
}
