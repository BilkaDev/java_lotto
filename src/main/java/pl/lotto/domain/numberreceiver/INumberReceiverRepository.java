package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface INumberReceiverRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketByDrawDate(LocalDateTime drawDate);

    Optional<Ticket> findByHash(String hash);
}
