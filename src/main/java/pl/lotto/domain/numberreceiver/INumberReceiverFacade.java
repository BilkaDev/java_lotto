package pl.lotto.domain.numberreceiver;

import pl.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface INumberReceiverFacade {
    NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) throws ValidationException;

    List<TicketDto> retrieveAllTicketsByNextDrawDate();

    List<TicketDto> retrieveAllTicketsByNextDrawDate(LocalDateTime drawDate);

    TicketDto retrieveTicketByHash(String hash) throws TicketNotFoundException;

}
