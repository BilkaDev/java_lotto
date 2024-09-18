package pl.lotto.infrastructure.security.jwt;

import pl.lotto.domain.common.BaseException;
import pl.lotto.domain.common.Code;
import pl.lotto.domain.common.HttpStatus;

public class TokenExpiredException extends BaseException {
    public TokenExpiredException() {
        super(Code.A3.name(), Code.A3.getLabel());
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
