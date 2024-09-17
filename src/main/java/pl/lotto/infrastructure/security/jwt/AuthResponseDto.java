package pl.lotto.infrastructure.security.jwt;

import lombok.Getter;
import pl.lotto.domain.common.Code;

import java.sql.Timestamp;


@Getter
public class AuthResponseDto {
    private final String timestamp;
    private final String message;
    private final String code;


    public AuthResponseDto(Code code) {
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.message = code.getLabel();
        this.code = code.toString();
    }
}

