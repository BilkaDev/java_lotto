package pl.lotto.domain.numbergenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.SixRandomNumbersDto;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class NumberGeneratorFacade {
    private final IRandomNumberGenerable randomNumberGenerable;
    private final IWinningNumbersValidator winningNumbersValidator;
    private final IWinningNumbersRepository winningNumbersRepository;
    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final NumberGeneratorFacadeConfigurationProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();

        SixRandomNumbersDto sixRandomNumbersDto = randomNumberGenerable
                .generateSixRandomNumbers(properties.count(), properties.lowerBand(), properties.upperBand());
        Set<Integer> winningNumbers = sixRandomNumbersDto.numbers();

        winningNumbersValidator.validate(winningNumbers);

        WinningNumbers savedNumbers = winningNumbersRepository.save(WinningNumbers.builder()
                .numbers(winningNumbers)
                .drawDate(nextDrawDate)
                .build());

        return WinningNumbersDto.builder()
                .winningNumbers(savedNumbers.getNumbers())
                .drawDate(savedNumbers.getDrawDate())
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime drawDate) {
        WinningNumbers winningNumbers = winningNumbersRepository.findByDrawDate(drawDate)
                .orElseThrow(WinningNumbersNotFoundException::new);
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.getNumbers())
                .drawDate(winningNumbers.getDrawDate())
                .build();

    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();
        return winningNumbersRepository.existsByDrawDate(nextDrawDate);
    }
}
