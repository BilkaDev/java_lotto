package pl.lotto.infrastructure.resultchecker.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

@Component
@AllArgsConstructor
@Log4j2
public class ResultCheckerScheduler {
    private final ResultCheckerFacade resultCheckerFacade;

    @Scheduled(cron = "${lotto.result-checker.resultCheckOccurrencesCron}")
    public void generateResults() {
        log.info("Start generating result");
        resultCheckerFacade.generateResults();
        log.info("Result generated");
    }
}
