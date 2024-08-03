package pl.lotto.domain.drawdategenerator;

import pl.lotto.domain.drawdategenerator.dto.DrawDateDto;

public interface IDrawDateGeneratorFacade {
    DrawDateDto retrieveNextDrawDate();
}
