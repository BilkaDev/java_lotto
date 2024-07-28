package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static pl.lotto.domain.resultannouncer.MessageResponse.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultResponseRepository resultResponseRepository;
    private final ResultCheckerFacade resultCheckerFacade;
    private final Clock clock;

    public ResultResponseDto checkResult(String hash) {
        if (resultResponseRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponseCached = resultResponseRepository.findById(hash);
            if (resultResponseCached.isPresent()) {
                return ResultResponseDto.builder().message(ALREADY_CHECKED.info)
                        .responseDto(ResultMapper.mapToDto(resultResponseCached.get())).build();
            }
        }
        ResultDto resultDto = resultCheckerFacade.findByTicketId(hash);
        if (resultDto == null) {
            return ResultResponseDto.builder()
                    .responseDto(null).message(MessageResponse.HASH_DOES_NOT_EXIST_MESSAGE.info).build();
        }
        ResponseDto responseDto = buildResponseDto(resultDto);
        resultResponseRepository.save(buildResponse(responseDto, LocalDateTime.now(clock)));
        if (resultResponseRepository.existsById(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return ResultResponseDto.builder().message(WAIT_MESSAGE.info).responseDto(responseDto).build();
        }
        if (resultCheckerFacade.findByTicketId(hash).isWinner()) {
            return ResultResponseDto.builder().message(WIN_MESSAGE.info).responseDto(responseDto).build();
        }
        return ResultResponseDto.builder().message(LOSE_MESSAGE.info).responseDto(responseDto).build();
    }

    private static ResultResponse buildResponse(ResponseDto responseDto, LocalDateTime now) {
        return ResultResponse.builder()
                .hash(responseDto.hash())
                .numbers(responseDto.numbers())
                .hitNumbers(responseDto.hitNumbers())
                .wonNumbers(responseDto.wonNumbers())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .createdDate(now).build();
    }

    private static ResponseDto buildResponseDto(ResultDto resultDto) {
        return ResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .wonNumbers(resultDto.wonNumbers())
                .build();
    }

    private boolean isAfterResultAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = resultDto.drawDate();
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }
}
