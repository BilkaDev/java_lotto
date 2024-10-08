package pl.lotto.domain.resultchecker;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numbergenerator.INumberGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.INumberReceiverFacade;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultchecker.dto.PlayersDto;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ResultCheckerFacade {
    private final INumberReceiverFacade numberReceiverFacade;
    private final INumberGeneratorFacade numberGeneratorFacade;
    private final IPlayerRepository playerRepository;
    private final WinnerGenerator winnerGenerator;

    public PlayersDto generateResults() {
        List<TicketDto> allTickets = numberReceiverFacade.retrieveAllTicketsByNextDrawDate();
        List<Ticket> tickets = ResultCheckerMapper.mapFromTicketDto(allTickets);
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        Set<Integer> winningNumbers = winningNumbersDto.winningNumbers();
        if (winningNumbers == null || winningNumbers.isEmpty()) {
            return PlayersDto.builder()
                    .message("Winners failed to retrieve")
                    .build();
        }

        List<Player> players = winnerGenerator.retrieveWinners(tickets, winningNumbers);
        playerRepository.saveAll(players);

        return PlayersDto.builder()
                .results(ResultCheckerMapper.mapPlayersToResults(players))
                .message("Winners succeeded to retrieve")
                .build();
    }


    public ResultDto findByTicketId(String ticketId) throws PlayerResultNotFoundException {
        Player player = playerRepository.findById(ticketId)
                .orElseThrow(() -> new PlayerResultNotFoundException("Not found for id: " + ticketId));
        return ResultDto.builder()
                .hash(ticketId)
                .numbers(player.getNumbers())
                .hitNumbers(player.getHitNumbers())
                .drawDate(player.getDrawDate())
                .wonNumbers(player.getWonNumbers())
                .isWinner(player.isWinner())
                .build();
    }
}
