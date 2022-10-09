package group14.feedapp.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends BaseRuntimeException {
    public ResourceAlreadyExistsException(String id) {
        super("Resource with " + id + " already exists", HttpStatus.CONFLICT);
    }
}
