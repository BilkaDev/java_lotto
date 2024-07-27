package pl.lotto.domain.drawdategenerator;

import lombok.AllArgsConstructor;
import pl.lotto.domain.drawdategenerator.dto.DrawDateDto;

import java.time.LocalDateTime;

@AllArgsConstructor
public class DrawDateGeneratorFacade {
    private final IDrawDateGenerable drawDataGenerator;

    public DrawDateDto retrieveNextDrawDate() {
        LocalDateTime nextDrawDate = drawDataGenerator.getNextDrawDate();
        return DrawDateDto.builder()
                .drawDate(nextDrawDate)
                .build();
    }
}
