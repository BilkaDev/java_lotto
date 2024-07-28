package pl.lotto.domain.resultchecker;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnerGenerator {
    private final static int WINNING_NUMBERS_COUNT = 3;

    public List<Player> retrieveWinners(List<Ticket> tickets, Set<Integer> winningNumbers) {
        return tickets.stream().map(ticket -> {
            Set<Integer> hitNumbers = calculateHitsNumbers(winningNumbers, ticket);
            return buildResult(winningNumbers, ticket, hitNumbers);
        }).toList();
    }

    private static Player buildResult(Set<Integer> winningNumbers, Ticket ticket, Set<Integer> hitNumbers) {
        Player.PlayerBuilder builder = Player.builder();
        if (isWinner(hitNumbers)) {
            builder.isWinner(true);
        }
        return builder
                .hash(ticket.hash())
                .numbers(ticket.numbers())
                .hitNumbers(hitNumbers)
                .drawDate(ticket.drawDate())
                .wonNumbers(winningNumbers)
                .build();
    }

    private static boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= WINNING_NUMBERS_COUNT;
    }

    private Set<Integer> calculateHitsNumbers(Set<Integer> winningNumbers, Ticket ticket) {
        return ticket.numbers().stream().filter(winningNumbers::contains).collect(Collectors.toSet());
    }
}
