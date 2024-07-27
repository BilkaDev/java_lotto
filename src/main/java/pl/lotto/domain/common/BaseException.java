package pl.lotto.domain.common;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    public abstract HttpStatus getStatusCode();

    private final String code;

    public BaseException(String message, String code) {
        super(message);
        this.code = code;
    }


}