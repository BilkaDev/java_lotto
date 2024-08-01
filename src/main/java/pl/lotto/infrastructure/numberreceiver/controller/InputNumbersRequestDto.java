package pl.lotto.infrastructure.numberreceiver.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record InputNumbersRequestDto(
        @NotNull(message = "{inputNumbers.not.null}")
        @NotEmpty(message = "{inputNumbers.not.empty}")
        List<Integer> numbers
) {
}
