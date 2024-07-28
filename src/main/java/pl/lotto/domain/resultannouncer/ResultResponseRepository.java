package pl.lotto.domain.resultannouncer;

import java.util.Optional;

public interface ResultResponseRepository {
    ResultResponse save(ResultResponse resultResponse);

    Optional<ResultResponse> findById(String hash);

    boolean existsById(String hash);
}
