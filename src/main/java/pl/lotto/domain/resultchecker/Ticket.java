package pl.lotto.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(
        LocalDateTime drawDate, String hash, Set<Integer> numbers
) {
}
