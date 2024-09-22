package pl.lotto.domain.loginandregister;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.Code;

public class UserExistingWithLoginException extends BaseException {
    public UserExistingWithLoginException() {
        super(Code.A4.getLabel(), Code.A4.name());
    }
}
