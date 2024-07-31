package pl.lotto.domain.resultchecker;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {
}
