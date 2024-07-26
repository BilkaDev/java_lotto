package pl.lotto.domain.numberreceiver;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.AdjustableClock;
import pl.lotto.domain.numberreceiver.dto.InputNumbersResultDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NumberReceiverFacadeTest {
    // given

    AdjustableClock clock = new AdjustableClock(
            LocalDateTime.of(2024, 6, 27, 12, 0, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC")
    );

    NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(
            new NumberValidator(),
            new InMemoryNumberReceiverRepositoryTestImpl(),
            clock
    );

    @Test
    public void should_return_success_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 99);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo("Success");
    }

    @Test
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_of_1_99() {
        // given
        Set<Integer> numbersFromUser = Set.of(100, 2, 3, 4, 5, 6);
        // when
        InputNumbersResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo("Failed");
    }

    @Test
    public void should_return_save_to_database_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 7);
        LocalDateTime drawDate = LocalDateTime.of(2024, 6, 27, 12, 0, 0)
                .toInstant(ZoneOffset.UTC).atZone(ZoneId.of("UTC")).toLocalDateTime();
        // when
        InputNumbersResultDto inputNumbersResultDto = numberReceiverFacade.inputNumbers(numbersFromUser);
        List<TicketDto> ticketDtos = numberReceiverFacade.userNumbers(drawDate);
        // then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .ticketId(inputNumbersResultDto.ticketId())
                        .drawDate(drawDate)
                        .numbersFromUsers(numbersFromUser)
                        .build()
        );
    }


}