package pl.lotto.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;

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
