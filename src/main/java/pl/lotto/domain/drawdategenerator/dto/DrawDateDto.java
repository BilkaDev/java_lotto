package pl.lotto.domain.drawdategenerator.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DrawDateDto(LocalDateTime drawDate) {
}
