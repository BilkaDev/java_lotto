package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

public interface IRandomNumberGenerable {
    SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerBand, int upperBand);
}
