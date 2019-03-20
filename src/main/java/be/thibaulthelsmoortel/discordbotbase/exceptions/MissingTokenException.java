package be.thibaulthelsmoortel.discordbotbase.exceptions;

/**
 * Exception raised when the API token is missing.
 *
 * @author Thibault Helsmoortel
 */
public class MissingTokenException extends IllegalArgumentException {

    public MissingTokenException() {
        super("No token provided. Make sure to add it to token.properties.");
    }
}
