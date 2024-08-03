package pl.lotto.domain.resultannouncer;

import pl.lotto.domain.resultannouncer.dto.ResultResponseDto;

public interface IResultAnnouncerFacade {

    ResultResponseDto checkResult(String hash);
}
