package pl.lotto.infrastructure.resultannoucer.controller;

import lombok.Builder;

@Builder
public record CheckResultResponseDto(
        CheckResultDto result,
        String message
) {
}
