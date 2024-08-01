package pl.lotto.infrastructure.numberreceiver.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.util.HashSet;
import java.util.Set;

@RestController()
@RequestMapping("/api/v1")
@Log4j2
@AllArgsConstructor
public class NumberReceiverController {
    private final NumberReceiverFacade numberReceiverFacade;

    @PostMapping("/inputNumbers")
    public ResponseEntity<InputNumbersResponseDto> inputNumbers(
            @RequestBody @Valid InputNumbersRequestDto numbersDto
    ) {
        log.info("Start POST inputNumbers");
        Set<Integer> numbers = new HashSet<>(numbersDto.numbers());
        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(numbers);
        TicketDto ticketDto = numberReceiverResponseDto.ticketDto();

        TicketResponseDto ticket = buildTicket(ticketDto);
        InputNumbersResponseDto responseDto = buildResponse(ticket, numberReceiverResponseDto);
        log.info("Stop POST inputNumbers");
        return ResponseEntity.ok(responseDto);

    }

    private static InputNumbersResponseDto buildResponse(TicketResponseDto ticket, NumberReceiverResponseDto numberReceiverResponseDto) {
        return InputNumbersResponseDto.builder()
                .ticket(ticket)
                .message(numberReceiverResponseDto.message())
                .build();
    }

    private static TicketResponseDto buildTicket(TicketDto ticketDto) {
        return TicketResponseDto.builder()
                .numbers(ticketDto.numbersFromUsers())
                .drawDate(ticketDto.drawDate())
                .ticketId(ticketDto.hash())
                .build();
    }
}
