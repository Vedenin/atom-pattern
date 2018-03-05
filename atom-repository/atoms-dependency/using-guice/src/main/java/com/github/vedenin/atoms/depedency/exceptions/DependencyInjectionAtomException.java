package com.github.vedenin.atoms.depedency.exceptions;

/**
 * Exception when program can't activate TrustManager
 * Created by Slava Vedenin on 6/6/2016.
 */
public class DependencyInjectionAtomException extends RuntimeException {
    public DependencyInjectionAtomException() {
    }

    public DependencyInjectionAtomException(String message) {
        super(message);
    }

    public DependencyInjectionAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyInjectionAtomException(Throwable cause) {
        super(cause);
    }

    public DependencyInjectionAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
