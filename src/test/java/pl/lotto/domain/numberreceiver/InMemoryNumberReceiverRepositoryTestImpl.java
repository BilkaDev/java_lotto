package pl.lotto.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryNumberReceiverRepositoryTestImpl implements INumberReceiverRepository {
    Map<String, Ticket> inMemoryDataBase = new ConcurrentHashMap<>();

    @Override
    public Ticket save(Ticket ticket) {
        inMemoryDataBase.put(ticket.getUuid(), ticket);
        return ticket;
    }

    @Override
    public List<Ticket> findAllTicketByDrawDate(LocalDateTime drawDate) {
        return inMemoryDataBase.values()
                .stream()
                .filter(
                        ticket -> ticket.getDrawDate().isEqual(drawDate)
                )
                .toList();
    }
}
