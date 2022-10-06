package group14.feedapp.exception;

import org.springframework.http.HttpStatus;

public class NoAccessException extends BaseRuntimeException {

    public NoAccessException(String userId) {
        super(String.format("User with user id %s doesn't have access to the requested resource.", userId), HttpStatus.UNAUTHORIZED);
    }
}
