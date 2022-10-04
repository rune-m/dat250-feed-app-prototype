package group14.feedapp.exception;

import org.springframework.http.HttpStatus;

public class BaseRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
