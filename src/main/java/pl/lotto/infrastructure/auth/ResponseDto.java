package pl.lotto.infrastructure.auth;

import lombok.Builder;

@Builder
public record ResponseDto(String message, String code) {
}
