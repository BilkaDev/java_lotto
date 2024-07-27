package pl.lotto.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WinningWinningNumbersRepositoryImplTest implements IWinningNumbersRepository {
    Map<String, WinningNumbers> winningNumbers = new ConcurrentHashMap<>();

    @Override
    public WinningNumbers save(WinningNumbers winningNumbers) {
        this.winningNumbers.put(winningNumbers.getDrawDate().toString(), winningNumbers);
        return winningNumbers;
    }

    @Override
    public Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate) {
        return winningNumbers.values().stream()
                .filter(winningNumbers -> winningNumbers.getDrawDate().equals(drawDate))
                .findFirst();
    }

    @Override
    public boolean existsByDate(LocalDateTime nextDrawDate) {
        return winningNumbers.values().stream()
                .anyMatch(winningNumbers -> winningNumbers.getDrawDate().equals(nextDrawDate));
    }

}
