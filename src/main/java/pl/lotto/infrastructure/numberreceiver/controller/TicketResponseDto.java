package pl.lotto.infrastructure.numberreceiver.controller;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record TicketResponseDto(LocalDateTime drawDate, String ticketId, Set<Integer> numbers) {
}
