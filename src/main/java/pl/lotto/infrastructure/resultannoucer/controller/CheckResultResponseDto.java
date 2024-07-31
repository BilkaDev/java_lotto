package pl.lotto.infrastructure.resultannoucer.controller;

import lombok.Builder;

@Builder
public record CheckResultResponseDto(
        ResultDto result,
        String message
) {
}
