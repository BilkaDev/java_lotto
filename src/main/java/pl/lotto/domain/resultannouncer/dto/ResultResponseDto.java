package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

@Builder
public record ResultResponseDto(
        ResponseDto responseDto,
        String message
) {
}
