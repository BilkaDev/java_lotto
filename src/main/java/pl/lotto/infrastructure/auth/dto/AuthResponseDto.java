package pl.lotto.infrastructure.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponseDto(String message, String code) {
}
