package pl.lotto.domain.numbergenerator;

import java.util.Set;

class WinningWinningNumbersValidator implements IWinningNumbersValidator {
    private final int LOWER_BAND = 1;
    private final int UPPER_BAND = 99;
    private final int REQUIRED_SIZE = 6;

    @Override
    public void validate(Set<Integer> winningNumbers) throws IllegalArgumentException {
        if (isRequiredSize(winningNumbers)) {
            throw new IllegalArgumentException("Required size is 6");
        }
        winningNumbers.forEach(number -> {
            if (isOutOfRange(number)) {
                throw new IllegalArgumentException("Number out of range");
            }
        });
    }

    private boolean isRequiredSize(Set<Integer> winningNumbers) {
        return winningNumbers.size() != REQUIRED_SIZE;
    }

    private boolean isOutOfRange(Integer number) {
        return number < LOWER_BAND || number > UPPER_BAND;
    }
}
