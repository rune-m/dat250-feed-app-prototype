package group14.feedapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorResponse> generalHandler(BaseRuntimeException exception) {
        HttpStatus httpStatus = exception.getHttpStatus();

        ErrorResponse response = new ErrorResponse(
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                exception.getMessage()
        );

        return new ResponseEntity<>(response, httpStatus);
    }
}
