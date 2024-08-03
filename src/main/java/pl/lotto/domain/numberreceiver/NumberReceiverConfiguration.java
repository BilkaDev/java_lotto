package pl.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.IDrawDateGeneratorFacade;

@Configuration
class NumberReceiverConfiguration {

    @Bean
    IHashGenerable hashGenerator() {
        return new HashGenerator();
    }

    @Bean
    NumberReceiverFacade numberReceiverFacade(
            IHashGenerable hashGenerator,
            INumberReceiverRepository numberReceiverRepository,
            IDrawDateGeneratorFacade drawDateGeneratorFacade
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
