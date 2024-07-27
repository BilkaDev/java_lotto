package pl.lotto.domain.numbergenerator;

import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;

class NumberGeneratorConfiguration {

    NumberGeneratorFacade numberGeneratorFacade(
            IRandomNumberGenerable randomNumberGenerable,
            IWinningNumbersRepository winningNumbersRepository,
            DrawDateGeneratorFacade drawDateGeneratorFacade
    ) {
        return new NumberGeneratorFacade(
                randomNumberGenerable,
                new WinningWinningNumbersValidator(),
                winningNumbersRepository,
                drawDateGeneratorFacade
        );
    }
}
