package pl.lotto.infrastructure.numberreceiver.controller;

import lombok.Builder;

import java.util.List;

@Builder
public record InputNumbersRequestDto(List<Integer> numbers) {
}
