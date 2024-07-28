package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

class ResultAnnouncerConfiguration {
    public ResultAnnouncerFacade createForTest(
            ResultResponseRepository resultResponseRepository,
            ResultCheckerFacade resultCheckerFacade,
            Clock clock
    ) {
        return new ResultAnnouncerFacade(
                resultResponseRepository,
                resultCheckerFacade,
                clock
        );
    }
}
