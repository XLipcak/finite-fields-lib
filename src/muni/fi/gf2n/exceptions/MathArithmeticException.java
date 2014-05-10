package muni.fi.gf2n.exceptions;

/**
 * MathArithmeticException is thrown to indicate, that operation has no
 * solution for arguments, which have been passed to the method.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class MathArithmeticException extends MathIllegalArgumentException {

    public MathArithmeticException() {
    }

    public MathArithmeticException(String message) {
        super(message);
    }

    public MathArithmeticException(Throwable cause) {
        super(cause);
    }

    public MathArithmeticException(String message, Throwable cause) {
        super(message, cause);
    }
}
