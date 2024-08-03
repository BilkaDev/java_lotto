package pl.lotto.domain.resultannouncer;


import org.junit.jupiter.api.Test;
import pl.lotto.domain.resultannouncer.dto.ResponseDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;
import pl.lotto.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.lotto.domain.resultannouncer.MessageResponse.*;

class ResultAnnouncerFacadeTest {
    private final ResultResponseRepository resultResponseRepository = new ResultResponseRepositoryTestImpl();
    private final ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);

    @Test
    public void should_return_response_with_lose_message_if_ticket_is_not_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, Clock.systemUTC());
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of())
                .drawDate(drawDate)
                .isWinner(false)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, LOSE_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_response_with_win_message_if_ticket_is_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, Clock.systemUTC());
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, WIN_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_response_with_wait_message_if_date_is_before_announcement_time() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        Clock clock = Clock.fixed(LocalDateTime.of(2024, 7, 23, 12, 0, 0).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, clock);
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResponseDto responseDto = ResponseDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();

        ResultResponseDto expectedResult = new ResultResponseDto(responseDto, WAIT_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_response_with_hash_does_not_exist_message_if_hash_does_not_exist() {
        //given
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, Clock.systemUTC());

        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(null);
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultResponseDto expectedResult = new ResultResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

    @Test
    public void it_should_return_response_with_hash_does_not_exist_message_if_response_is_not_saved_to_db_yet() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        ResultDto resultDto = ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);

        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, Clock.systemUTC());
        ResultResponseDto resultResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultResponseDto1.responseDto().hash();
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultResponseDto expectedResult = new ResultResponseDto(
                resultResponseDto.responseDto()
                , ALREADY_CHECKED.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

}