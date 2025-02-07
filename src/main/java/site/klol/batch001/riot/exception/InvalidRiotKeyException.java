package site.klol.batch001.riot.exception;

import site.klol.batch001.common.exception.NoSkipException;

public class InvalidRiotKeyException extends NoSkipException {
    public InvalidRiotKeyException() {
    }

    public InvalidRiotKeyException(String message) {
        super(message);
    }

    public InvalidRiotKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRiotKeyException(Throwable cause) {
        super(cause);
    }

    public InvalidRiotKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
