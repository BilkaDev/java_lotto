package pl.lotto.domain.drawdategenerator;

import java.time.LocalDateTime;

public interface IDrawDateGenerable {
    LocalDateTime getNextDrawDate();
}
