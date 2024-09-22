package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.common.BaseException;

public class WinningNumbersNotFoundException extends BaseException {
    public WinningNumbersNotFoundException() {
        super("winning numbers not found", "WINNING_NUMBERS_NOT_FOUND");
    }
}
