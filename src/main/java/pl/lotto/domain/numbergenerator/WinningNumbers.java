package pl.lotto.domain.numbergenerator;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Document
public class WinningNumbers {
    @Id
    private String id;
    private Set<Integer> numbers;
    private LocalDateTime drawDate;
}
