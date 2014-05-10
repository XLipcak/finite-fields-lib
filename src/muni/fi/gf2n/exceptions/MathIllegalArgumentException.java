package muni.fi.gf2n.exceptions;

/**
 * MathIllegalArgumentException is superclass of all exceptions thrown by
 * this library. 
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class MathIllegalArgumentException extends IllegalArgumentException {

    public MathIllegalArgumentException() {
    }

    public MathIllegalArgumentException(String message) {
        super(message);
    }

    public MathIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public MathIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
