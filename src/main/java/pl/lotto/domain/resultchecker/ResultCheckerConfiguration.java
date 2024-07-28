package pl.lotto.domain.resultchecker;

import pl.lotto.domain.numbergenerator.NumberGeneratorFacade;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

class ResultCheckerConfiguration {
    ResultCheckerFacade createForTests(
            NumberGeneratorFacade numberGeneratorFacade,
            NumberReceiverFacade numberReceiverFacade,
            PlayerRepository playerRepository) {
        WinnerGenerator winnerGenerator = new WinnerGenerator();
        return new ResultCheckerFacade(
                numberReceiverFacade,
                numberGeneratorFacade,
                playerRepository,
                winnerGenerator
        );
    }
}
