package pl.lotto.infrastructure.numberreceiver.controller;


import lombok.Builder;

@Builder
public record InputNumbersResponseDto(TicketResponseDto ticket, String message) {
}
