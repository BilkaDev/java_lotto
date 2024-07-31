package pl.lotto.domain.resultchecker;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Getter
@Document
public class Player {
    @Id
    private String hash;
    private Set<Integer> numbers;
    private Set<Integer> hitNumbers;
    private LocalDateTime drawDate;
    private boolean isWinner;
    private Set<Integer> wonNumbers;
}