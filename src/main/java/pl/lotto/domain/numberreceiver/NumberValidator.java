package pl.lotto.domain.numberreceiver;

import java.util.Set;

class NumberValidator implements INumberValidator {
    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 99;

    public boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .filter(number -> number >= MIN_NUMBER && number <= MAX_NUMBER)
                .count() == MAX_NUMBERS_FROM_USER;
    }
}
