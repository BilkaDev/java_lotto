package pl.lotto.domain.resultannouncer;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import pl.lotto.domain.numberreceiver.INumberReceiverFacade;
import pl.lotto.domain.numberreceiver.TicketNotFoundException;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultannouncer.dto.ResultDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;
import pl.lotto.domain.resultchecker.PlayerResultNotFoundException;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import static pl.lotto.domain.resultannouncer.MessageResponse.*;

@AllArgsConstructor
public class ResultAnnouncerFacade implements IResultAnnouncerFacade {
    private final ResultResponseRepository resultResponseRepository;
    private final ResultCheckerFacade resultCheckerFacade;
    private final INumberReceiverFacade numberReceiverFacade;
    private final Clock clock;

    @Override
    @Cacheable(cacheNames = "results")
    public ResultResponseDto checkResult(String hash) throws TicketNotFoundException {
        TicketDto ticketDto = numberReceiverFacade.retrieveTicketByHash(hash);

        if (resultResponseRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponseCached = resultResponseRepository.findById(hash);
            if (resultResponseCached.isPresent()) {
                return ResultResponseDto.builder().message(ALREADY_CHECKED.info)
                        .resultDto(ResultMapper.mapToDto(resultResponseCached.get())).build();
            }
        }
        pl.lotto.domain.resultchecker.dto.ResultDto resultDto;
        try {
            resultDto = resultCheckerFacade.findByTicketId(hash);
        } catch (PlayerResultNotFoundException e) {
            return ResultResponseDto.builder()
                    .resultDto(
                            ResultDto.builder()
                                    .hash(hash)
                                    .numbers(ticketDto.numbersFromUsers())
                                    .hitNumbers(null)
                                    .drawDate(ticketDto.drawDate())
                                    .isWinner(false)
                                    .wonNumbers(null)
                                    .build()
                    ).message(WAIT_MESSAGE.info).build();
        }

        ResultDto responseDto = buildResponseDto(resultDto);
        resultResponseRepository.save(buildResponse(responseDto, LocalDateTime.now(clock)));
        if (resultResponseRepository.existsById(hash) && !isAfterResultAnnouncementTime(resultDto)) {
            return ResultResponseDto.builder().message(WAIT_MESSAGE.info).resultDto(responseDto).build();
        }
        if (resultDto.isWinner()) {
            return ResultResponseDto.builder().message(WIN_MESSAGE.info).resultDto(responseDto).build();
        }
        return ResultResponseDto.builder().message(LOSE_MESSAGE.info).resultDto(responseDto).build();
    }

    private static ResultResponse buildResponse(ResultDto resultDto, LocalDateTime now) {
        return ResultResponse.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .wonNumbers(resultDto.wonNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .createdDate(now).build();
    }

    private static ResultDto buildResponseDto(pl.lotto.domain.resultchecker.dto.ResultDto resultDto) {
        return ResultDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .wonNumbers(resultDto.wonNumbers())
                .build();
    }

    private boolean isAfterResultAnnouncementTime(pl.lotto.domain.resultchecker.dto.ResultDto resultDto) {
        LocalDateTime announcementDateTime = resultDto.drawDate();
        return LocalDateTime.now(clock).isAfter(announcementDateTime);
    }
}
