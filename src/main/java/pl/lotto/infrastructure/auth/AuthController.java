package pl.lotto.infrastructure.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lotto.infrastructure.auth.dto.AuthResponseDto;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;
import pl.lotto.infrastructure.auth.dto.LoginResponseDto;
import pl.lotto.infrastructure.auth.dto.RegisterRequestDto;
import pl.lotto.infrastructure.auth.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Log4j2
public class AuthController {
    private final UserService userService;

    @GetMapping("/auto-login")
    public ResponseEntity<LoginResponseDto> autoLogin(HttpServletRequest request) {
        log.info("--TRY AUTO LOGIN USER");
        return this.userService.autoLogin(request);
    }


    @GetMapping("/logged-in")
    public ResponseEntity<AuthResponseDto> loggedIn(HttpServletRequest request) {
        log.info("--TRY LOGGED IN USER");
        return userService.loggedIn(request);
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthResponseDto> logout(HttpServletRequest request,HttpServletResponse response) {
        log.info("--TRY LOGOUT USER");
        return this.userService.logout(request, response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequest,
            HttpServletResponse response
    ) {
        log.info("--TRY LOGIN USER");
        return this.userService.login(loginRequest, response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerUserDto) {
        log.info("--START REGISTER USER");
        ResponseEntity<AuthResponseDto> register = this.userService.register(registerUserDto);
        log.info("--END REGISTER USER");
        return register;
    }
}