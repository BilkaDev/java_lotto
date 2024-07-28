package pl.lotto.domain.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;

@Configuration
class NumberGeneratorConfiguration {

    @Bean
    NumberGeneratorFacade numberGeneratorFacade(
            IRandomNumberGenerable randomNumberGenerable,
            IWinningNumbersRepository winningNumbersRepository,
            DrawDateGeneratorFacade drawDateGeneratorFacade,
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

    NumberGeneratorFacade createForTest(
            IRandomNumberGenerable randomNumberGenerable,
            IWinningNumbersRepository winningNumbersRepository,
            DrawDateGeneratorFacade drawDateGeneratorFacade
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
