package site.klol.batch.common.exception;

public class NoSkipException extends RuntimeException {
    public NoSkipException() {
    }

    public NoSkipException(String message) {
        super(message);
    }

    public NoSkipException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSkipException(Throwable cause) {
        super(cause);
    }

    public NoSkipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
