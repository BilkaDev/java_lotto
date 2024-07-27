package pl.lotto.domain.numberreceiver;

import pl.lotto.domain.numberreceiver.dto.TicketDto;

class TicketMapper {
    public static TicketDto mapFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .hash(ticket.getHash())
                .drawDate(ticket.getDrawDate())
                .numbersFromUsers(ticket.getNumbersFromUsers())
                .build();
    }
}
