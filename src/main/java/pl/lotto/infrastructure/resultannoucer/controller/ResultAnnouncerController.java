package pl.lotto.infrastructure.resultannoucer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.domain.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;

@RestController
@RequestMapping("/api/v1/results")
@AllArgsConstructor
@Log4j2
public class ResultAnnouncerController {
    private final ResultAnnouncerFacade resultAnnouncerFacade;

    @GetMapping("/{id}")
    public ResponseEntity<CheckResultResponseDto> checkResultsById(
            @PathVariable("id") String ticketId
    ) {
        log.info("Start announcing results");
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(ticketId);
        ResponseDto responseDto = resultResponseDto.responseDto();

        CheckResultResponseDto responseBody = buildResponse(responseDto, resultResponseDto);
        log.info("Stop announcing results");
        return ResponseEntity.ok(responseBody);
    }

    private static CheckResultResponseDto buildResponse(ResponseDto responseDto, ResultResponseDto resultResponseDto) {
        return CheckResultResponseDto.builder()
                .result(buildResult(responseDto))
                .message(resultResponseDto.message())
                .build();
    }

    private static ResultDto buildResult(ResponseDto responseDto) {
        return ResultDto
                .builder()
                .hitNumbers(responseDto.hitNumbers())
                .hash(responseDto.hash())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .numbers(responseDto.numbers())
                .wonNumbers(responseDto.wonNumbers())
                .build();
    }
}
