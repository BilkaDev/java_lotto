package pl.lotto.domain.drawdategenerator;

import java.time.Clock;

class DrawDateGeneratorConfiguration {

    Clock clock() {
        return Clock.systemUTC();
    }

    DrawDateGeneratorFacade drawDateGeneratorFacade(Clock clock) {
        return new DrawDateGeneratorFacade(new DrawDateGenerator(clock));
    }
}
