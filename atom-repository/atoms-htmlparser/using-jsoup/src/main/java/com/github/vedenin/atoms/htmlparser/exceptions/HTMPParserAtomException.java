package com.github.vedenin.atoms.htmlparser.exceptions;

/**
 * Exception from DocumentAtom
 * Created by Slava Vedenin on 6/6/2016.
 */
@SuppressWarnings("unused")
public class HTMPParserAtomException extends RuntimeException {
    public HTMPParserAtomException() {
    }

    public HTMPParserAtomException(String message) {
        super(message);
    }

    public HTMPParserAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTMPParserAtomException(Throwable cause) {
        super(cause);
    }

    public HTMPParserAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
