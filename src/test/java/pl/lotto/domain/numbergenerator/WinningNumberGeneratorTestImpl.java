package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.util.Set;

public class WinningNumberGeneratorTestImpl implements IRandomNumberGenerable {

    private final Set<Integer> numbers;

    WinningNumberGeneratorTestImpl(Set<Integer> numbers) {
        this.numbers = numbers;
    }

    WinningNumberGeneratorTestImpl() {
        this.numbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(numbers)
                .build();
    }


}
