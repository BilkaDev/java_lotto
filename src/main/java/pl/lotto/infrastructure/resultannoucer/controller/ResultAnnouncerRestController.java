package pl.lotto.infrastructure.resultannoucer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.numberreceiver.TicketNotFoundException;
import pl.lotto.domain.resultannouncer.IResultAnnouncerFacade;
import pl.lotto.domain.resultannouncer.dto.ResultDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;
import pl.lotto.infrastructure.httpexceptions.HttpException;

@RestController
@RequestMapping("/api/v1/results")
@AllArgsConstructor
@Log4j2
public class ResultAnnouncerRestController {
    private final IResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/{id}")
    public ResponseEntity<CheckResultResponseDto> checkResultsById(
            @PathVariable("id") String ticketId
    ) {
        log.info("Start announcing results");
        try {
            ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(ticketId);
            ResultDto resultDto = resultResponseDto.resultDto();
            CheckResultResponseDto responseDto = buildResponse(resultDto, resultResponseDto);
            log.info("Stop announcing results");
            return ResponseEntity.ok(responseDto);
        } catch (TicketNotFoundException e) {
            throw new HttpException(HttpStatus.NOT_FOUND, e);
        }

    }

    private static CheckResultResponseDto buildResponse(ResultDto resultDto, ResultResponseDto resultResponseDto) {
        return CheckResultResponseDto.builder()
                .result(buildResult(resultDto))
                .message(resultResponseDto.message())
                .build();
    }

    private static CheckResultDto buildResult(ResultDto resultDto) {
        return CheckResultDto
                .builder()
                .hitNumbers(resultDto.hitNumbers())
                .hash(resultDto.hash())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .numbers(resultDto.numbers())
                .wonNumbers(resultDto.wonNumbers())
                .build();
    }
}
