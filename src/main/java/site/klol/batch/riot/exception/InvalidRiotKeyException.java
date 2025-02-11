package site.klol.batch.riot.exception;

import org.springframework.http.HttpStatus;
import site.klol.batch.common.exception.NoSkipException;

public class InvalidRiotKeyException extends NoSkipException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String errorCode = String.valueOf(HttpStatus.BAD_REQUEST.value());

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
