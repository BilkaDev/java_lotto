package pl.lotto.infrastructure.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRequestDto(
        @Size(min = 4, max = 50)
        @NotBlank(message = "{login.not.blank}") String login,
        @Size(min = 8, max = 75)
        @NotBlank(message = "{password.not.blank") String password,
        @Email(message = "{email.not.valid}") String email) {
}
