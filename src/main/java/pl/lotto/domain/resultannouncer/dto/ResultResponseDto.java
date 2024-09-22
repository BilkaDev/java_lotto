package pl.lotto.domain.resultannouncer.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ResultResponseDto(
        ResultDto resultDto,
        String message
) implements Serializable {
}
