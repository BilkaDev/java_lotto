package pl.lotto.infrastructure.security.jwt;

import org.springframework.http.HttpStatus;
import pl.lotto.domain.common.Code;
import pl.lotto.infrastructure.httpexceptions.HttpException;

public class TokenExpiredException extends HttpException {
    public TokenExpiredException() {
        super(HttpStatus.UNAUTHORIZED, Code.A3.name(), Code.A3.getLabel());
    }
}
