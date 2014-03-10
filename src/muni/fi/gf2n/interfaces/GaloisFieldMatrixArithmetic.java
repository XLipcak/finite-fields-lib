/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.gf2n.interfaces;

import java.util.ArrayList;
import java.util.List;
import muni.fi.gf2n.classes.Matrix;
import muni.fi.gf2n.classes.Vector;

/**
 *
 * @author Jakub Lipcak, Masaryk University
 */
public interface GaloisFieldMatrixArithmetic {
    Matrix add(Matrix matrix1, Matrix matrix2);
    Matrix subtract(Matrix matrix1, Matrix matrix2);
    
    //matrix*matrix
    Matrix multiply(Matrix matrix1, Matrix matrix2);
    
    //matrix*scalar
    Matrix multiply(Matrix matrix, long scalarValue);
    
    //matrix*vector
    Matrix multiply(Matrix matrix, Vector vector);
    Matrix multiply(Vector vector, Matrix matrix);
    
    Matrix inverse(Matrix matrix);
    Matrix power(Matrix matrix, long exponent);
    long determinant(Matrix matrix);
    int rank(Matrix matrix);
    Matrix gauss(Matrix matrix);

    //returnVector*equationMatrix = results
    Vector solveLinearEquationsSystem(Matrix equationMatrix, Vector results);
    
    Matrix image(Matrix matrix);
    Matrix kernel(Matrix matrix);
}
