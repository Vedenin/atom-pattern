package com.github.vedenin.atoms.collections.exceptions;

/**
 * Exception from Collection atoms
 * Created by Slava Vedenin on 6/6/2016.
 */
public class CollectionAtomException extends RuntimeException {
    public CollectionAtomException() {
    }

    public CollectionAtomException(String message) {
        super(message);
    }

    public CollectionAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CollectionAtomException(Throwable cause) {
        super(cause);
    }

    public CollectionAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
