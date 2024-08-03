package pl.lotto.infrastructure.numbergenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbergenerator.INumberGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;

@Component
@AllArgsConstructor
@Log4j2
public class WinningNumbersScheduler {

    private final INumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "${lotto.number-generator.lotteryRunOccurrencesCron}")
    public void generateWinningNumbers() {
        log.info("Start generating winning numbers");
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        log.info("Winning numbers generated: {}", winningNumbersDto.winningNumbers());
    }

}
