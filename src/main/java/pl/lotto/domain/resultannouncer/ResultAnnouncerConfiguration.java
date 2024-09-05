package pl.lotto.domain.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numberreceiver.INumberReceiverFacade;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;

@Configuration
class ResultAnnouncerConfiguration {
    @Bean
    IResultAnnouncerFacade resultAnnouncerFacade(
            ResultResponseRepository resultResponseRepository,
            ResultCheckerFacade resultCheckerFacade,
            INumberReceiverFacade numberReceiverFacade,
            Clock clock
    ) {
        return new ResultAnnouncerFacade(
                resultResponseRepository,
                resultCheckerFacade,
                numberReceiverFacade,
                clock
        );
    }

    IResultAnnouncerFacade createForTest(
            ResultResponseRepository resultResponseRepository,
            ResultCheckerFacade resultCheckerFacade,
            INumberReceiverFacade numberReceiverFacade,
            Clock clock
    ) {
        return resultAnnouncerFacade(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, clock);
    }
}
