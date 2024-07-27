package pl.lotto.domain.numbergenerator;

import java.util.Set;

public interface IWinningNumbersValidator {
    void validate(Set<Integer> numbers) throws IllegalArgumentException;
}
