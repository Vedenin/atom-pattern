package com.github.vedenin.atoms.restclient.exceptions;

/**
 * Exception when program can't activate TrustManager
 * Created by Slava Vedenin on 6/6/2016.
 */
public class RestClientAtomException extends RuntimeException {
    public RestClientAtomException() {
    }

    public RestClientAtomException(String message) {
        super(message);
    }

    public RestClientAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestClientAtomException(Throwable cause) {
        super(cause);
    }

    public RestClientAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
