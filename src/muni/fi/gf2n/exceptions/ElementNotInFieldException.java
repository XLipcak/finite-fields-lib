package muni.fi.gf2n.exceptions;

/**
 * ElementNotInFieldException is thrown to indicate, that a method has been 
 * passed elements out of field.
 *
 * @author Jakub Lipcak, Masaryk University
 */
public class ElementNotInFieldException extends MathIllegalArgumentException {

    public ElementNotInFieldException() {
    }

    public ElementNotInFieldException(String message) {
        super(message);
    }

    public ElementNotInFieldException(Throwable cause) {
        super(cause);
    }

    public ElementNotInFieldException(String message, Throwable cause) {
        super(message, cause);
    }
}
