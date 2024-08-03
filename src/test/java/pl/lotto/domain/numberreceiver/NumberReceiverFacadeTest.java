package pl.lotto.domain.numberreceiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lotto.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.lotto.domain.drawdategenerator.IDrawDateGeneratorFacade;
import pl.lotto.domain.drawdategenerator.dto.DrawDateDto;
import pl.lotto.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberReceiverFacadeTest {
    // given
    IHashGenerable hashGenerator = new HashGeneratorTestImpl();

    INumberReceiverRepository numberReceiverRepository = new InMemoryNumberReceiverRepositoryTestImpl();
    IDrawDateGeneratorFacade drawDateGeneratorFacade = mock(DrawDateGeneratorFacade.class);
    INumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().numberReceiverFacade(
            hashGenerator,
            numberReceiverRepository,
            drawDateGeneratorFacade
    );

    @BeforeEach
    public void setUp() {
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                LocalDateTime.of(2024, 7, 27, 10, 0, 0)
        ).build());
    }


    @Test
    public void should_return_success_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 99);
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                LocalDateTime.now()
        ).build());
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.retrieveNextDrawDate().drawDate();

        TicketDto generatedTicket = TicketDto.builder()
                .hash(hashGenerator.getHash())
                .drawDate(nextDrawDate)
                .numbersFromUsers(numbersFromUser)
                .build();

        // when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);

        // then
        NumberReceiverResponseDto expected = NumberReceiverResponseDto.builder()
                .ticketDto(
                        generatedTicket
                )
                .message(ValidationResult.INPUT_SUCCESS.info)
                .build();
        assertThat(result.message()).isEqualTo(ValidationResult.INPUT_SUCCESS.info);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void should_return_failed_when_user_gave_less_than_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5);
        // when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo(ValidationResult.NOT_SIX_NUMBERS_GIVEN.info);
    }

    @Test
    public void should_return_failed_when_user_gave_more_than_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6, 7);
        // when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo(ValidationResult.NOT_SIX_NUMBERS_GIVEN.info);
    }

    @Test
    public void should_return_failed_when_user_gave_at_least_one_number_out_of_range_of_1_99() {
        // given
        Set<Integer> numbersFromUser = Set.of(100, 2, 3, 4, 5, 6);
        // when
        NumberReceiverResponseDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo(ValidationResult.NOT_IN_RANGE.info);
    }

    @Test
    public void should_return_correct_hash() {
        // given
        IHashGenerable hashGenerator = new HashGenerator();
        INumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().numberReceiverFacade(
                hashGenerator, numberReceiverRepository, drawDateGeneratorFacade);
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);

        // when
        String response = numberReceiverFacade.inputNumbers(numbersFromUser).ticketDto().hash();

        // then
        assertThat(response).hasSize(8);
        assertThat(response).isNotNull();
    }


    @Test
    public void should_return_save_to_database_when_user_gave_six_numbers() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 7);
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0)
                .toInstant(ZoneOffset.UTC).atZone(ZoneId.of("UTC")).toLocalDateTime();
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                drawDate
        ).build());
        // when
        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(numbersFromUser);
        List<TicketDto> ticketDtos = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        // then
        assertThat(ticketDtos).contains(
                TicketDto.builder()
                        .hash(numberReceiverResponseDto.ticketDto().hash())
                        .drawDate(drawDate)
                        .numbersFromUsers(numbersFromUser)
                        .build()
        );
    }


    @Test
    public void should_return_tickets_with_correct_draw_date() {
        // given
        IHashGenerable hashGenerator = new HashGenerator();

        LocalDateTime date = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                date
        ).build());

        INumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().numberReceiverFacade(hashGenerator, numberReceiverRepository, drawDateGeneratorFacade);

        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        NumberReceiverResponseDto numberReceiverResponseDto1 = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                date.plusWeeks(1L)
        ).build());
        NumberReceiverResponseDto numberReceiverResponseDto2 = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        NumberReceiverResponseDto numberReceiverResponseDto3 = numberReceiverFacade.inputNumbers(Set.of(1, 2, 3, 4, 5, 6));
        TicketDto ticketDto = numberReceiverResponseDto.ticketDto();
        TicketDto ticketDto1 = numberReceiverResponseDto1.ticketDto();
        LocalDateTime drawDate = numberReceiverResponseDto.ticketDto().drawDate();
        // when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        // then
        assertThat(allTicketsByDate).containsOnly(ticketDto, ticketDto1);
    }

    @Test
    public void should_return_empty_collections_if_there_are_no_tickets() {
        // given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);

        // when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate);
        // then
        assertThat(allTicketsByDate).isEmpty();
    }

    @Test
    public void should_return_empty_collections_if_given_date_is_after_next_drawDate() {
        // given
        when(drawDateGeneratorFacade.retrieveNextDrawDate()).thenReturn(DrawDateDto.builder().drawDate(
                LocalDateTime.now()
        ).build());
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 6);
        NumberReceiverResponseDto numberReceiverResponseDto = numberReceiverFacade.inputNumbers(numbersFromUser);

        LocalDateTime drawDate = numberReceiverResponseDto.ticketDto().drawDate();

        // when
        List<TicketDto> allTicketsByDate = numberReceiverFacade.retrieveAllTicketsByNextDrawDate(drawDate.plusWeeks(1L));
        // then
        assertThat(allTicketsByDate).isEmpty();
    }

    @Test
    public void should_return_ticket_by_hash() {
        // given
        Set<Integer> numbersFromUser = Set.of(1, 2, 3, 4, 5, 99);
        NumberReceiverResponseDto savedTicket = numberReceiverFacade.inputNumbers(numbersFromUser);

        // when
        TicketDto result = numberReceiverFacade.retrieveTicketByHash(savedTicket.ticketDto().hash());

        // then
        assertThat(result).isEqualTo(savedTicket.ticketDto());
    }

    @Test
    public void should_throw_exception_when_hash_dont_exist() {
        assertThrows(TicketNotFoundException.class, () -> numberReceiverFacade.retrieveTicketByHash("notExistingHash"));
    }
}