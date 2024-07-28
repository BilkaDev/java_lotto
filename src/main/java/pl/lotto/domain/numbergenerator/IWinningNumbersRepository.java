package pl.lotto.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IWinningNumbersRepository extends MongoRepository<WinningNumbers, String> {
    Optional<WinningNumbers> findByDrawDate(LocalDateTime drawDate);

    boolean existsByDrawDate(LocalDateTime nextDrawDate);
}
