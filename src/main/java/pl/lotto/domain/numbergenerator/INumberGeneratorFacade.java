package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;

import java.time.LocalDateTime;

public interface INumberGeneratorFacade {

    WinningNumbersDto generateWinningNumbers();

    WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime drawDate);

    boolean areWinningNumbersGeneratedByDate();
}
