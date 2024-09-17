package pl.lotto.domain.loginandregister;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.Code;
import pl.lotto.domain.common.HttpStatus;

public class UserExistingWithLoginException extends BaseException {
    public UserExistingWithLoginException() {
        super(Code.A4.getLabel(), Code.A4.name());
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
