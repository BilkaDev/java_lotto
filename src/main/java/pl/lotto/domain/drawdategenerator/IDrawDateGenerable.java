package pl.lotto.domain.drawdategenerator;

import java.time.LocalDateTime;

interface IDrawDateGenerable {
    LocalDateTime getNextDrawDate();
}
