package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.HttpStatus;

public class WinningNumbersNotFoundException extends BaseException {
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    public WinningNumbersNotFoundException() {
        super("winning numbers not found", "WINNING_NUMBERS_NOT_FOUND");
    }
}
