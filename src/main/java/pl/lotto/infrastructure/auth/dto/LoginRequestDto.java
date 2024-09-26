package pl.lotto.infrastructure.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(

        @NotBlank(message = "{login.not.blank}")
        @Size(min = 4, max = 50)
        String login,
        @Size(min = 8, max = 75)
        @NotBlank(message = "{password.not.blank}")
        String password
) {
}
