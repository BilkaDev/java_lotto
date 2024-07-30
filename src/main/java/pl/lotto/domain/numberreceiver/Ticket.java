package pl.lotto.domain.numberreceiver;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Document
public class Ticket {
    @Id
    private String hash;
    private LocalDateTime drawDate;
    private Set<Integer> numbersFromUsers;
}
