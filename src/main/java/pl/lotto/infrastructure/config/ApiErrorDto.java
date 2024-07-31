package pl.lotto.infrastructure.config;

import lombok.Builder;
import pl.lotto.domain.common.HttpStatus;

import java.util.List;

@Builder
public record ApiErrorDto(
        List<String> messages,
        HttpStatus status,
        String code
) {
}
