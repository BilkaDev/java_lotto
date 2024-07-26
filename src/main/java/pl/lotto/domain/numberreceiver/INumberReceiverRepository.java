package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;

public interface INumberReceiverRepository {
    Ticket save(Ticket ticket);

    List<Ticket> findAllTicketByDrawDate(LocalDateTime drawDate);
}
