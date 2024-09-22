package pl.lotto.infrastructure.auth.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(String login, String email) {
}
