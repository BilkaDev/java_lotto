package pl.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.Collections;
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
    private IHashGenerable hashGenerator;
    private IDrawDateGenerable drawDateGenerator;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {

        String validationResult = numberValidator.validation(numbersFromUser);
        if (!validationResult.isEmpty())
            return NumberReceiverResponseDto.builder().message(validationResult).build();


        String hash = hashGenerator.getHash();
        LocalDateTime drawDate = drawDateGenerator.getNextDrawDate();
        Ticket ticket = Ticket.builder()
                .hash(hash)
                .numbersFromUsers(numbersFromUser)
                .drawDate(drawDate)
                .build();

        Ticket savedTicket = numberReceiverRepository.save(ticket);

        return NumberReceiverResponseDto.builder()
                .ticketDto(TicketMapper.mapFromTicket(savedTicket))
                .message(ValidationResult.INPUT_SUCCESS.info)
                .build();
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate() {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        return retrieveAllTicketsByNextDrawDate(nextDrawDate);
    }

    public List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime drawDate) {
        LocalDateTime nextDrawDate = drawDateGenerator.getNextDrawDate();
        if (drawDate.isAfter(nextDrawDate)) {
            return Collections.emptyList();
        }
        List<Ticket> tickets = numberReceiverRepository.findAllTicketByDrawDate(drawDate);
        return tickets.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

    public LocalDateTime retrieveNextDrawDate() {
        return drawDateGenerator.getNextDrawDate();
    }

    public TicketDto retrieveTicketByHash(String hash) {
        return numberReceiverRepository.findByHash(hash)
                .map(TicketMapper::mapFromTicket)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found", hash));
    }
}
