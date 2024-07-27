package pl.lotto.domain.numberreceiver;

import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;

class NumberReceiverConfiguration {

    HashGenerator hashGenerator() {
        return new HashGenerator();
    }

    NumberReceiverFacade numberReceiverFacade(
            IHashGenerable hashGenerator,
            INumberReceiverRepository numberReceiverRepository,
            DrawDateGeneratorFacade drawDateGeneratorFacade
    ) {
        NumberValidator numberValidator = new NumberValidator();
        return new NumberReceiverFacade(
                numberValidator,
                numberReceiverRepository,
                hashGenerator,
                drawDateGeneratorFacade
        );
    }

}
