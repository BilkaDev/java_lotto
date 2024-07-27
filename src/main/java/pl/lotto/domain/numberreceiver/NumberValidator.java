package pl.lotto.domain.numberreceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class NumberValidator implements INumberValidator {
    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 99;

    public List<ValidationResult> errors = new ArrayList<>();

    @Override
    public String validation(Set<Integer> numbersFromUser) {
        if (!areNumbersInRange(numbersFromUser)) {
            errors.add(ValidationResult.NOT_IN_RANGE);
        }
        if (!areNumbersEqualSix(numbersFromUser)) {
            errors.add(ValidationResult.NOT_SIX_NUMBERS_GIVEN);
        }
        return createResultMessage();
    }

    private String createResultMessage() {
        return this.errors.stream()
                .map(error -> error.info)
                .collect(Collectors.joining(", "));
    }

    private boolean areNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .allMatch(number -> number >= MIN_NUMBER && number <= MAX_NUMBER);
    }

    private boolean areNumbersEqualSix(Set<Integer> numbersFromUser) {
        return numbersFromUser.size() == MAX_NUMBERS_FROM_USER;
    }
}
