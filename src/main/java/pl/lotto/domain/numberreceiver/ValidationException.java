package pl.lotto.domain.numberreceiver;

import lombok.Getter;
import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.HttpStatus;

@Getter
public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message, "Validation Exception");
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
