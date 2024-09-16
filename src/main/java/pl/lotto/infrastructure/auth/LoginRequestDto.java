package pl.lotto.infrastructure.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(

        @NotBlank(message = "{login.not.blank}")
        String login,
        @NotBlank(message = "{password.not.blank}")
        String password
) {
}
