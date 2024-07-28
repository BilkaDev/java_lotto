package pl.lotto.domain.resultchecker;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Getter
public class Player {
    private String hash;
    private Set<Integer> numbers;
    private Set<Integer> hitNumbers;
    private LocalDateTime drawDate;
    private boolean isWinner;
    private Set<Integer> wonNumbers;
}