package pl.lotto.infrastructure.httpexceptions;

import org.springframework.http.HttpStatus;
import pl.lotto.domain.common.BaseException;

public class HttpException extends BaseException {
    private final HttpStatus status;

    public HttpException(HttpStatus status, BaseException error) {
        super(error.getMessage(), error.getCode());
        this.status = status;
    }

    public HttpException(HttpStatus status, String message, String code) {
        super(message, code);
        this.status = status;
    }

    public HttpStatus getStatusCode() {
        return status;
    }
}
