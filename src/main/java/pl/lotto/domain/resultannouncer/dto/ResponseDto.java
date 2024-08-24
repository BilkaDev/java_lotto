package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ResponseDto(
        String hash,
        Set<Integer> numbers,
        Set<Integer> wonNumbers,
        Set<Integer> hitNumbers,
        LocalDateTime drawDate,
        boolean isWinner
) implements Serializable {
}
