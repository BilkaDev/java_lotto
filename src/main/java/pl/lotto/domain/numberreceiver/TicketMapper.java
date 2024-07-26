package pl.lotto.domain.numberreceiver;

import pl.lotto.domain.numberreceiver.dto.TicketDto;

public class TicketMapper {
    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .ticketId(ticket.getUuid())
                .drawDate(ticket.getDrawDate())
                .numbersFromUsers(ticket.getNumbersFromUsers())
                .build();
    }
}
