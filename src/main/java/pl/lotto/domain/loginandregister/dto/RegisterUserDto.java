package pl.lotto.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record RegisterUserDto(String login, String password, String email) {
}
