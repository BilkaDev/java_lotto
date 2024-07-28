package pl.lotto.domain.resultchecker;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.HttpStatus;

class PlayerResultNotFoundException extends BaseException {
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    public PlayerResultNotFoundException(String message) {
        super(message, "PLAYER_RESULT_NOT_FOUND");
    }
}
