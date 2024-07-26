package pl.lotto.domain.numberreceiver;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class Ticket extends TicketBaseEntity {
    private LocalDateTime drawDate;
    private Set<Integer> numbersFromUsers;
}
