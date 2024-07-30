package pl.lotto.domain.numberreceiver;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface INumberReceiverRepository extends MongoRepository<Ticket, String> {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketByDrawDate(LocalDateTime drawDate);

    Optional<Ticket> findByHash(String hash);
}
