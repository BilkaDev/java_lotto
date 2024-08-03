package pl.lotto.domain.drawdategenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class DrawDateGeneratorConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    IDrawDateGeneratorFacade drawDateGeneratorFacade(Clock clock) {
        return new DrawDateGeneratorFacade(new DrawDateGenerator(clock));
    }
}
