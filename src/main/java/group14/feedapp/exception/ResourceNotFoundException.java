package group14.feedapp.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseRuntimeException {
    public ResourceNotFoundException(String id) {
        super("Resource with " + id + " not found", HttpStatus.NOT_FOUND);
    }
}
