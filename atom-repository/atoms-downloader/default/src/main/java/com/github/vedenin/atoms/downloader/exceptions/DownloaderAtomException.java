package com.github.vedenin.atoms.downloader.exceptions;

/**
 * Exception when program can't activate TrustManager
 * Created by Slava Vedenin on 6/6/2016.
 */
public class DownloaderAtomException extends RuntimeException {
    public DownloaderAtomException() {
    }

    public DownloaderAtomException(String message) {
        super(message);
    }

    public DownloaderAtomException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloaderAtomException(Throwable cause) {
        super(cause);
    }

    public DownloaderAtomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
