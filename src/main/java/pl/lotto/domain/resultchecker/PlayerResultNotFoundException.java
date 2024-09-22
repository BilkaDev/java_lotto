package pl.lotto.domain.resultchecker;

import pl.lotto.domain.common.BaseException;

public class PlayerResultNotFoundException extends BaseException {
    public PlayerResultNotFoundException(String message) {
        super(message, "PLAYER_RESULT_NOT_FOUND");
    }
}
