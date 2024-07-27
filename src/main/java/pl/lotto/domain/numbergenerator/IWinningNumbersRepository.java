package pl.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IWinningNumbersRepository {
    WinningNumbers save(WinningNumbers winningNumbers);

    Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate);

    boolean existsByDate(LocalDateTime nextDrawDate);
}
