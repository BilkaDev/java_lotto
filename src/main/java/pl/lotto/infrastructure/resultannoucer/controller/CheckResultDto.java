package pl.lotto.infrastructure.resultannoucer.controller;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record CheckResultDto(
        String hash,
        Set<Integer> numbers,
        Set<Integer> wonNumbers,
        Set<Integer> hitNumbers,
        LocalDateTime drawDate,
        boolean isWinner
) {
}
