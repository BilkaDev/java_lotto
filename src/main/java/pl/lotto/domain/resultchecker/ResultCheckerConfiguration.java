package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbergenerator.INumberGeneratorFacade;
import pl.lotto.domain.numberreceiver.INumberReceiverFacade;

@Configuration
class ResultCheckerConfiguration {
    @Bean
    public ResultCheckerFacade resultCheckerFacade(
            INumberGeneratorFacade numberGeneratorFacade,
            INumberReceiverFacade numberReceiverFacade,
            IPlayerRepository playerRepository
    ) {
        WinnerGenerator winnerGenerator = new WinnerGenerator();
        return new ResultCheckerFacade(
                numberReceiverFacade,
                numberGeneratorFacade,
                playerRepository,
                winnerGenerator
        );
    }

    ResultCheckerFacade createForTests(
            INumberGeneratorFacade numberGeneratorFacade,
            INumberReceiverFacade numberReceiverFacade,
            IPlayerRepository playerRepository) {
        return resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
    }
}
