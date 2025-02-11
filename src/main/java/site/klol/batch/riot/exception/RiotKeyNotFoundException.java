package site.klol.batch.riot.exception;

import org.springframework.http.HttpStatus;
import site.klol.batch.common.exception.NoSkipException;

public class RiotKeyNotFoundException extends NoSkipException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String errorCode = String.valueOf(HttpStatus.BAD_REQUEST.value());

    public RiotKeyNotFoundException() {
    }

    public RiotKeyNotFoundException(String message) {
        super(message);
    }

    public RiotKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiotKeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public RiotKeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
