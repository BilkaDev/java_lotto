package pl.lotto.domain.drawdategenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DrawDateGeneratorFacadeTest {
    AdjustableClock clock = new AdjustableClock(
            LocalDateTime.of(2024, 7, 27, 10, 0, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC")
    );

    @Test
    public void should_return_next_draw_date() {
        // given
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorConfiguration().drawDateGeneratorFacade(clock);

        // when
        LocalDateTime testedDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();

        // then
        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        assertThat(testedDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_next_Saturday_draw_date_when_date_is_Saturday_noon() {
        // given
        clock.plusMinutes(118);
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorConfiguration().drawDateGeneratorFacade(clock);

        // when
        LocalDateTime testedDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();


        // then

        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);

        assertThat(testedDrawDate).isEqualTo(expectedDrawDate);
    }

    @Test
    public void should_return_next_Saturday_draw_date_when_date_is_Saturday_afternoon() {
        // given
        clock.plusMinutes(238);
        DrawDateGeneratorFacade drawDateGeneratorFacade = new DrawDateGeneratorConfiguration().drawDateGeneratorFacade(clock);

        // when
        LocalDateTime testedDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();

        // then

        LocalDateTime expectedDrawDate = LocalDateTime.of(2024, 8, 3, 12, 0, 0);

        assertThat(testedDrawDate).isEqualTo(expectedDrawDate);
    }
}