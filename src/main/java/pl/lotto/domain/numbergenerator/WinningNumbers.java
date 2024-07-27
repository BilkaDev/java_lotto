package pl.lotto.domain.numbergenerator;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
public class WinningNumbers {
    private String id;
    private Set<Integer> numbers;
    private LocalDateTime drawDate;
}
