package com.github.vedenin.atoms.xml.exceptions;

/**
 * Exception when program can't activate TrustManager
 * Created by Slava Vedenin on 6/6/2016.
 */
public class XMLAtomException extends RuntimeException {
    public XMLAtomException() {
    }

    public XMLAtomException(String message) {
        super(message);
    }

    public XMLAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLAtomException(Throwable cause) {
        super(cause);
    }

    public XMLAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
