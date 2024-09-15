package pl.lotto.domain.loginandregister;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.HttpStatus;


public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super("User not found", "User not found");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
