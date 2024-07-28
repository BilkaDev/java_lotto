package pl.lotto.domain.resultchecker;

import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.util.List;

public class ResultCheckerMapper {
    static List<Ticket> mapFromTicketDto(List<TicketDto> tickets) {
        return tickets.stream().map(ticketDto -> Ticket.builder()
                .hash(ticketDto.hash())
                .drawDate(ticketDto.drawDate())
                .numbers(ticketDto.numbersFromUsers())
                .build()).toList();
    }

    static List<ResultDto> mapPlayersToResults(List<Player> players) {
        return players.stream().map(player -> ResultDto.builder()
                .hash(player.getHash())
                .drawDate(player.getDrawDate())
                .numbers(player.getNumbers())
                .hitNumbers(player.getHitNumbers())
                .isWinner(player.isWinner())
                .wonNumbers(player.getWonNumbers())
                .build()).toList();
    }
}
