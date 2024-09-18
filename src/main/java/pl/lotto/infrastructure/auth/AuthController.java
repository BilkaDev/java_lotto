package pl.lotto.infrastructure.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;
import pl.lotto.infrastructure.auth.dto.RegisterRequestDto;
import pl.lotto.infrastructure.auth.services.UserService;
import pl.lotto.infrastructure.security.jwt.JwtAuthenticatorFacade;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Log4j2
public class AuthController {
    private final UserService userService;

    @GetMapping("/auto-login")
    public ResponseEntity<?> autoLogin(HttpServletRequest request, HttpServletResponse response) {
        log.info("--TRY AUTO LOGIN USER");
        return this.userService.autoLogin(request, response);
    }


    @GetMapping("/logged-in")
    public ResponseEntity<?> loggedIn() {
        log.info("--TRY LOGGED IN USER");
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        log.info("--TRY LOGOUT USER");
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequestDto loginRequest,
            HttpServletResponse response
    ) {
        log.info("--TRY LOGIN USER");
        return this.userService.login(loginRequest, response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody RegisterRequestDto registerUserDto) {
        log.info("--START REGISTER USER");
        ResponseEntity<ResponseDto> register = this.userService.register(registerUserDto);
        log.info("--END REGISTER USER");
        return register;
    }
}