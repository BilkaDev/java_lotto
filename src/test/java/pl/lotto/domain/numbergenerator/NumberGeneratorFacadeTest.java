package pl.lotto.domain.numbergenerator;

import org.junit.jupiter.api.Test;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NumberGeneratorFacadeTest {
    private final IWinningNumbersRepository winningNumbersRepository = new WinningWinningNumbersRepositoryImplTest();

    NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);

    @Test
    public void should_return_set_of_required_size() {
        // given
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new RandomNumbersGenerator(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());

        // when
        WinningNumbersDto generatedNumbers = numberGeneratorFacade.generateWinningNumbers();

        // then
        assertThat(generatedNumbers.winningNumbers().size()).isEqualTo(6);
    }

    @Test
    public void should_return_set_of_required_size_within_required_range() {
        // given
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new RandomNumbersGenerator(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());

        // when
        WinningNumbersDto generatedNumbers = numberGeneratorFacade.generateWinningNumbers();

        // then
        int upperBand = 99;
        int lowerBand = 1;
        Set<Integer> numbers = generatedNumbers.winningNumbers();
        numbers.forEach(number -> assertThat(number).isBetween(lowerBand, upperBand));

    }

    @Test
    public void should_throw_exception_when_size_is_bigger_than_required() {
        // given
        Set<Integer> numbersOutOfRange = Set.of(1, 2, 3, 4, 5, 6, 7);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(numbersOutOfRange),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        // then
        assertThrows(IllegalArgumentException.class, numberGeneratorFacade::generateWinningNumbers, "Required size is 6");
    }

    @Test
    public void should_throw_exception_when_size_is_lower_than_required() {
        // given
        Set<Integer> numbersOutOfRange = Set.of(1, 2, 3, 4, 5);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(numbersOutOfRange),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        // then
        assertThrows(IllegalArgumentException.class, numberGeneratorFacade::generateWinningNumbers, "Required size is 6");
    }

    @Test
    public void should_throw_exception_when_required_size_is_bigger_than_range() {
        // given
        Set<Integer> numbersOutOfRange = Set.of(1, 2, 3, 4, 5, 100);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(numbersOutOfRange),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        // then
        assertThrows(IllegalArgumentException.class, numberGeneratorFacade::generateWinningNumbers, "Number out of range");
    }

    @Test
    public void should_return_collections_of_unique_values() {
        //given
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(LocalDateTime.now());
        //when
        WinningNumbersDto generatedNumbers = numberGeneratorFacade.generateWinningNumbers();
        //then
        int generatedNumbersSize = new HashSet<>(generatedNumbers.winningNumbers()).size();
        assertThat(generatedNumbersSize).isEqualTo(6);
    }

    @Test
    public void should_return_winning_numbers_by_given_date() {
        // given
        LocalDateTime drawDate = LocalDateTime.of(2024, 7, 27, 12, 0, 0);
        Set<Integer> numbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .numbers(numbers)
                .drawDate(drawDate)
                .build();
        winningNumbersRepository.save(winningNumbers);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);

        // when
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.retrieveWinningNumbersByDate(drawDate);

        // then
        WinningNumbersDto expectedWinningNumbersDto = WinningNumbersDto.builder()
                .winningNumbers(numbers)
                .drawDate(drawDate)
                .build();
        assertThat(winningNumbersDto).isEqualTo(expectedWinningNumbersDto);
    }

    @Test
    public void it_should_throw_an_exception_when_fail_to_retrieve_numbers_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        //when
        //then
        assertThrows(WinningNumbersNotFoundException.class, () -> numberGeneratorFacade.retrieveWinningNumbersByDate(drawDate), "Not Found");
    }

    @Test
    public void should_return_true_if_numbers_are_generated_by_given_date() {
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 12, 17, 12, 0, 0);
        Set<Integer> generatedWinningNumbers = Set.of(1, 2, 3, 4, 5, 6);
        String id = UUID.randomUUID().toString();
        WinningNumbers winningNumbers = WinningNumbers.builder()
                .id(id)
                .drawDate(drawDate)
                .numbers(generatedWinningNumbers)
                .build();
        winningNumbersRepository.save(winningNumbers);
        when(numberReceiverFacade.retrieveNextDrawDate()).thenReturn(drawDate);
        NumberGeneratorFacade numberGeneratorFacade = new NumberGeneratorConfiguration().numberGeneratorFacade(
                new WinningNumberGeneratorTestImpl(),
                numberReceiverFacade,
                winningNumbersRepository
        );
        //when
        boolean areWinningNumbersGeneratedByDate = numberGeneratorFacade.areWinningNumbersGeneratedByDate();
        //then
        assertTrue(areWinningNumbersGeneratedByDate);

    }
}