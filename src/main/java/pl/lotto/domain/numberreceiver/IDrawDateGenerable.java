package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;

public interface IDrawDateGenerable {
    LocalDateTime getNextDrawDate();
}
