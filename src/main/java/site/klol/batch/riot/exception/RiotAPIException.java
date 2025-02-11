package site.klol.batch.riot.exception;

import org.springframework.http.HttpStatus;

public class RiotAPIException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public RiotAPIException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.errorCode = String.valueOf(status.value());
    }

    public RiotAPIException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = String.valueOf(status.value());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
