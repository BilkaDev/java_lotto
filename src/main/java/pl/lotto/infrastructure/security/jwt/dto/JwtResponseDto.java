package pl.lotto.infrastructure.security.jwt.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String login, String token, String email, Long tokenExp) {
}
