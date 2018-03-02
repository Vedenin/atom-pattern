package com.github.vedenin.atoms.io.exceptions;

/**
 * Exception in FileAndIOUtilsAtom
 * Created by Slava Vedenin on 6/6/2016.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class IOAtomException extends RuntimeException {
    public IOAtomException() {
    }

    public IOAtomException(String message) {
        super(message);
    }

    public IOAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOAtomException(Throwable cause) {
        super(cause);
    }

    public IOAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
