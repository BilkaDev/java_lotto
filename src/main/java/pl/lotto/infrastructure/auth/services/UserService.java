package pl.lotto.infrastructure.auth.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lotto.domain.common.Code;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.infrastructure.auth.ResponseDto;
import pl.lotto.infrastructure.auth.dto.AuthResponseDto;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;
import pl.lotto.infrastructure.auth.dto.LoginResponseDto;
import pl.lotto.infrastructure.auth.dto.RegisterRequestDto;
import pl.lotto.infrastructure.security.jwt.JwtAuthenticatorFacade;
import pl.lotto.infrastructure.security.jwt.TokenExpiredException;
import pl.lotto.infrastructure.security.jwt.dto.JwtResponseDto;

import java.util.Arrays;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder bcryptEncoder;
    private final JwtAuthenticatorFacade jwtAuthenticatorFacade;
    private final CookieService cookieService;

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.authenticate(loginRequestDto);
        Cookie cookie = cookieService.generateCookie("Authorization", jwtResponseDto.token(), jwtResponseDto.tokenExp().intValue() * 24 * 60 * 60);

        response.addCookie(cookie);
        return ResponseEntity.ok(LoginResponseDto.builder()
                .email(jwtResponseDto.email())
                .login(jwtResponseDto.login())
                .build()
        );
    }

    public ResponseEntity<ResponseDto> register(RegisterRequestDto registerUserDto) {

        loginAndRegisterFacade.register(RegisterUserDto.builder()
                .login(registerUserDto.login())
                .password(bcryptEncoder.encode(registerUserDto.password()))
                .email(registerUserDto.email())
                .build());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .code(Code.SUCCESS.name())
                        .message("User registered successfully")
                        .build()
                );
    }

    public ResponseEntity<LoginResponseDto> autoLogin(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie value : Arrays.stream(cookies).toList()) {
                if (value.getName().equals("Authorization")) {
                    token = value.getValue();
                }
            }
        } else {
            log.info("Can't login because token is empty");
            throw new TokenExpiredException();
        }
        JwtResponseDto jwtResponseDto = jwtAuthenticatorFacade.loginByToken(token);
        return ResponseEntity.ok(LoginResponseDto.builder()
                .email(jwtResponseDto.email())
                .login(jwtResponseDto.login())
                .build());
    }

    public ResponseEntity<AuthResponseDto> loggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie value : Arrays.stream(cookies).toList()) {
                if (value.getName().equals("Authorization")) {
                    token = value.getValue();
                }
            }
        }
        boolean loggedIn = this.jwtAuthenticatorFacade.loggedIn(token);
        if (loggedIn) {
            return ResponseEntity.ok(AuthResponseDto.builder()
                    .code(Code.PERMIT.name())
                    .message(Code.PERMIT.getLabel())
                    .build());
        }
        return ResponseEntity.ok(AuthResponseDto.builder()
                .code(Code.DENY.name())
                .message(Code.DENY.getLabel())
                .build());
    }

    public ResponseEntity<AuthResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout: Delete all cookies");
        Cookie cookie = cookieService.removeCookie(request.getCookies(), "Authorization");
        if (cookie != null) {
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(AuthResponseDto.builder()
                .code(Code.SUCCESS.name())
                .message(Code.SUCCESS.getLabel())
                .build());
    }
}
