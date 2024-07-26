package pl.lotto.domain.numberreceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InputNumbersResultDto(String message, LocalDateTime drawDate, String ticketId) {
}
