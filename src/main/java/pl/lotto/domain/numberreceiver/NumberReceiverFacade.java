package pl.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/*
- user provided 6 numbers: 1,2,3,4,5,6
- numbers must be in range from 1 -99
- numbers can not be repeated
- customer gets information about the date of the draw
- user got a unique coupon
* */
@AllArgsConstructor
public class NumberReceiverFacade {
    private INumberValidator numberValidator;
    private INumberReceiverRepository numberReceiverRepository;
    private Clock clock;

    public InputNumbersResultDto inputNumbers(Set<Integer> numbersFromUser) {

        boolean areAllNumbersInRange = numberValidator.areAllNumbersInRange(numbersFromUser);
        if (!areAllNumbersInRange)
            return InputNumbersResultDto.builder().message("Failed").build();

        Ticket ticket = Ticket.builder()
                .numbersFromUsers(numbersFromUser)
                .drawDate(LocalDateTime.now(clock))
                .build();

        Ticket savedTicket = numberReceiverRepository.save(ticket);

        return InputNumbersResultDto.builder()
                .drawDate(LocalDateTime.now(clock))
                .ticketId(savedTicket.getUuid())
                .message("Success").build();

    }

    public List<TicketDto> userNumbers(LocalDateTime drawDate) {
        List<Ticket> tickets = numberReceiverRepository.findAllTicketByDrawDate(drawDate);
        return tickets.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

}
