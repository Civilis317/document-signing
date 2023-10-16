package net.playground.documentsigningservice.exception;

import java.util.Arrays;

/**
 * The ApplicationException is thrown when the application encounters an unresolvable error which cannot be fixed by the user.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String message, Object... values) {
        super(ApplicationException.format(message, values));
    }

    private static String format(String message, Object[] values) {
        final String msg = message == null ? "" : message;
        Arrays.stream(values).map(Object::toString).forEach(value -> msg.replaceFirst("\\{}", value));
        return msg;
    }

}
