package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class RandomNumbersGenerator implements IRandomNumberGenerable {
    private final int LOWER_BAND = 1;
    private final int UPPER_BAND = 99;

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        Set<Integer> randomNumbers = new HashSet<>();
        while (isAmountOfNumbersLowerThanSix(randomNumbers)) {
            int randomNumber = generateRandomNumber();
            randomNumbers.add(randomNumber);
        }
        return SixRandomNumbersDto.builder()
                .numbers(randomNumbers)
                .build();
    }

    private boolean isAmountOfNumbersLowerThanSix(Set<Integer> randomNumbers) {
        return randomNumbers.size() < 6;
    }

    private int generateRandomNumber() {
        Random random = new SecureRandom();
        int RANDOM_NUMBER_BOUND = (UPPER_BAND - LOWER_BAND) + 1;
        return random.nextInt(RANDOM_NUMBER_BOUND) + LOWER_BAND;
    }
}
