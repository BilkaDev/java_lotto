package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ResultResponseDto(
        ResponseDto responseDto,
        String message
) implements Serializable {
}
