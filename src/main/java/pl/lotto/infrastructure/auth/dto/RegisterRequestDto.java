package pl.lotto.infrastructure.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @NotBlank(message = "{login.not.blank}") String login,
        @NotBlank(message = "{password.not.blank") String password,
        @Email(message = "{email.not.valid}") String email) {
}
