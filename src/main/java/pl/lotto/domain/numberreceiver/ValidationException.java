package pl.lotto.domain.numberreceiver;

import lombok.Getter;
import pl.lotto.domain.common.BaseException;

@Getter
public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message, "Validation Exception");
    }
}
