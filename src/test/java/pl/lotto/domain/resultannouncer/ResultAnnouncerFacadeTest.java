package pl.lotto.domain.resultannouncer;


import org.junit.jupiter.api.Test;
import pl.lotto.domain.numberreceiver.INumberReceiverFacade;
import pl.lotto.domain.numberreceiver.TicketNotFoundException;
import pl.lotto.domain.numberreceiver.dto.TicketDto;
import pl.lotto.domain.resultannouncer.dto.ResultDto;
import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;
import pl.lotto.domain.resultchecker.PlayerResultNotFoundException;
import pl.lotto.domain.resultchecker.ResultCheckerFacade;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.lotto.domain.resultannouncer.MessageResponse.*;

class ResultAnnouncerFacadeTest {
    private final ResultResponseRepository resultResponseRepository = new ResultResponseRepositoryTestImpl();
    private final ResultCheckerFacade resultCheckerFacade = mock(ResultCheckerFacade.class);
    private final INumberReceiverFacade numberReceiverFacade = mock(INumberReceiverFacade.class);

    @Test
    public void should_return_response_with_lose_message_if_ticket_is_not_winning_ticket() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, Clock.systemUTC());
        pl.lotto.domain.resultchecker.dto.ResultDto resultDto = pl.lotto.domain.resultchecker.dto.ResultDto.builder()
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
        ResultDto responseDto = ResultDto.builder()
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
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, Clock.systemUTC());
        pl.lotto.domain.resultchecker.dto.ResultDto resultDto = pl.lotto.domain.resultchecker.dto.ResultDto.builder()
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
        ResultDto responseDto = ResultDto.builder()
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
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, clock);
        pl.lotto.domain.resultchecker.dto.ResultDto resultDto = pl.lotto.domain.resultchecker.dto.ResultDto.builder()
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
        ResultDto responseDto = ResultDto.builder()
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
    public void it_should_return_response_with_wait_message_when_there_is_no_player_results() {
        //given
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, Clock.systemUTC());

        when(resultCheckerFacade.findByTicketId(hash)).thenThrow(new PlayerResultNotFoundException("Hash does not exist"));
        LocalDateTime drawDate = LocalDateTime.now();
        when(numberReceiverFacade.retrieveTicketByHash(hash)).thenReturn(TicketDto.builder().numbersFromUsers(Set.of(1)).drawDate(drawDate).build());

        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(hash);
        //then
        ResultResponseDto expectedResult = new ResultResponseDto(
                ResultDto.builder()
                        .hash("123")
                        .numbers(Set.of(1))
                        .hitNumbers(null)
                        .drawDate(drawDate)
                        .isWinner(false)
                        .wonNumbers(null)
                        .build(),
                WAIT_MESSAGE.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }
    @Test
    public void it_should_return_response_with_ticket_not_found_if_hash_does_not_exist() {
        //given
        String hash = "123";
        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, Clock.systemUTC());

        // when & then
        when(numberReceiverFacade.retrieveTicketByHash(hash)).thenThrow(new TicketNotFoundException("Ticket not found", hash));

        TicketNotFoundException ticketNotFoundException = assertThrows(TicketNotFoundException.class, () -> {
            resultAnnouncerFacade.checkResult(hash);
        });

        assertThat(ticketNotFoundException.getMessage()).isEqualTo("Ticket with hash 123 not found");

    }

        @Test
    public void it_should_return_response_with_hash_does_not_exist_message_if_response_is_not_saved_to_db_yet() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        String hash = "123";
        pl.lotto.domain.resultchecker.dto.ResultDto resultDto = pl.lotto.domain.resultchecker.dto.ResultDto.builder()
                .hash("123")
                .numbers(Set.of(1, 2, 3, 4, 5, 6))
                .hitNumbers(Set.of(1, 2, 3, 4, 9, 0))
                .drawDate(drawDate)
                .isWinner(true)
                .build();
        when(resultCheckerFacade.findByTicketId(hash)).thenReturn(resultDto);

        IResultAnnouncerFacade resultAnnouncerFacade = new ResultAnnouncerConfiguration().createForTest(resultResponseRepository, resultCheckerFacade, numberReceiverFacade, Clock.systemUTC());
        ResultResponseDto resultResponseDto1 = resultAnnouncerFacade.checkResult(hash);
        String underTest = resultResponseDto1.resultDto().hash();
        //when
        ResultResponseDto resultResponseDto = resultAnnouncerFacade.checkResult(underTest);
        //then
        ResultResponseDto expectedResult = new ResultResponseDto(
                resultResponseDto.resultDto()
                , ALREADY_CHECKED.info);
        assertThat(resultResponseDto).isEqualTo(expectedResult);
    }

}