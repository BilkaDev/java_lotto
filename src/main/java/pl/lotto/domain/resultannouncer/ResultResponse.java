package pl.lotto.domain.resultannouncer;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class ResultResponse {
    private String hash;
    Set<Integer> numbers;
    Set<Integer> wonNumbers;
    Set<Integer> hitNumbers;
    LocalDateTime drawDate;
    boolean isWinner;
    LocalDateTime createdDate;
}
