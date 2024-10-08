package pl.lotto.domain.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.IDrawDateGeneratorFacade;

@Configuration
class NumberGeneratorConfiguration {

    @Bean
    INumberGeneratorFacade numberGeneratorFacade(
            IRandomNumberGenerable randomNumberGenerable,
            IWinningNumbersRepository winningNumbersRepository,
            IDrawDateGeneratorFacade drawDateGeneratorFacade,
            NumberGeneratorFacadeConfigurationProperties properties
    ) {
        WinningWinningNumbersValidator winningWinningNumbersValidator = new WinningWinningNumbersValidator();
        return new NumberGeneratorFacade(
                randomNumberGenerable,
                winningWinningNumbersValidator,
                winningNumbersRepository,
                drawDateGeneratorFacade,
                properties
        );
    }

    INumberGeneratorFacade createForTest(
            IRandomNumberGenerable randomNumberGenerable,
            IWinningNumbersRepository winningNumbersRepository,
            IDrawDateGeneratorFacade drawDateGeneratorFacade
    ) {
        NumberGeneratorFacadeConfigurationProperties properties = NumberGeneratorFacadeConfigurationProperties.builder()
                .count(6)
                .lowerBand(1)
                .upperBand(99)
                .build();
        return numberGeneratorFacade(
                randomNumberGenerable,
                winningNumbersRepository,
                drawDateGeneratorFacade,
                properties
        );
    }
}
