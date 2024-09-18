package pl.lotto.infrastructure.auth.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.infrastructure.auth.ResponseDto;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;
import pl.lotto.infrastructure.auth.dto.LoginResponseDto;
import pl.lotto.infrastructure.auth.dto.RegisterRequestDto;
import pl.lotto.infrastructure.security.jwt.JwtAuthenticatorFacade;
import pl.lotto.infrastructure.security.jwt.dto.JwtResponseDto;

@Service
@AllArgsConstructor
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
                        .code("SUCCESS")
                        .message("User registered successfully")
                        .build()
                );
    }

    public ResponseEntity<?> autoLogin(HttpServletRequest request, HttpServletResponse response) {
        return jwtAuthenticatorFacade.loginByToken(request, response);
    }
}
