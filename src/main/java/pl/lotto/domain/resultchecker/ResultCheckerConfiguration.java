package pl.lotto.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numbergenerator.NumberGeneratorFacade;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

@Configuration
class ResultCheckerConfiguration {
    @Bean
    public ResultCheckerFacade resultCheckerFacade(
            NumberGeneratorFacade numberGeneratorFacade,
            NumberReceiverFacade numberReceiverFacade,
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
            NumberGeneratorFacade numberGeneratorFacade,
            NumberReceiverFacade numberReceiverFacade,
            IPlayerRepository playerRepository) {
        return resultCheckerFacade(numberGeneratorFacade, numberReceiverFacade, playerRepository);
    }
}
