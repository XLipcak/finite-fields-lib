package muni.fi.gf2n.exceptions;

/**
 * DimensionMismatchException is thrown to indicate, that a method has been 
 * passed objects with wrong dimensions and computation cannot be performed.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class DimensionMismatchException extends MathIllegalArgumentException {

    public DimensionMismatchException() {
    }

    public DimensionMismatchException(String message) {
        super(message);
    }

    public DimensionMismatchException(Throwable cause) {
        super(cause);
    }

    public DimensionMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
