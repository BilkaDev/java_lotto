package pl.lotto.domain.numberreceiver;

import lombok.Getter;
import pl.lotto.domain.common.BaseException;

@Getter
public class TicketNotFoundException extends BaseException {
    public TicketNotFoundException(String code, String hash) {
        super(String.format("Ticket with hash %s not found", hash), code);
    }
}
