package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

class NumberGeneratorConfiguration {

    NumberGeneratorFacade numberGeneratorFacade(
            IRandomNumberGenerable randomNumberGenerable,
            NumberReceiverFacade numberReceiverFacade,
            IWinningNumbersRepository winningNumbersRepository
    ) {
        return new NumberGeneratorFacade(
                randomNumberGenerable,
                numberReceiverFacade,
                new WinningWinningNumbersValidator(),
                winningNumbersRepository
        );
    }
}
