package com.github.vedenin.atoms.io.exceptions;

/**
 * RuntimeException if resources can't be open
 *
 * Created by vedenin on 04.06.16.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ResourceAtomException extends RuntimeException {
    public ResourceAtomException() {
    }

    public ResourceAtomException(String message) {
        super(message);
    }

    public ResourceAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAtomException(Throwable cause) {
        super(cause);
    }

    public ResourceAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
