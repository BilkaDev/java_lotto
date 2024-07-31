package pl.lotto.domain.resultannouncer;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Document
public class ResultResponse {
    @Id
    private String hash;
    Set<Integer> numbers;
    Set<Integer> wonNumbers;
    Set<Integer> hitNumbers;
    LocalDateTime drawDate;
    boolean isWinner;
    @Indexed(expireAfterSeconds = 10)
    LocalDateTime createdDate;
}
