package pl.lotto.domain.numberreceiver;

import java.time.Clock;

class NumberReceiverConfiguration {

    Clock clock() {
        return Clock.systemUTC();
    }

    HashGenerator hashGenerator() {
        return new HashGenerator();
    }

    NumberReceiverFacade numberReceiverFacade(
            IHashGenerable hashGenerator,
            Clock clock,
            INumberReceiverRepository numberReceiverRepository
    ) {
        NumberValidator numberValidator = new NumberValidator();
        DrawDataGenerator drawDataGenerator = new DrawDataGenerator(clock);
        return new NumberReceiverFacade(
                numberValidator,
                numberReceiverRepository,
                hashGenerator,
                drawDataGenerator
        );
    }

}
