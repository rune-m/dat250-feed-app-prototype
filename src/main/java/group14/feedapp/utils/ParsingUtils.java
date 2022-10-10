package group14.feedapp.utils;

import group14.feedapp.exception.BaseRuntimeException;
import org.springframework.http.HttpStatus;

public class ParsingUtils implements IParsingUtils {

    @Override
    public int parseIntToString(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new BaseRuntimeException("Pincode must be a number", HttpStatus.BAD_REQUEST);
        }
    }
}
