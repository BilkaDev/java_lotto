package pl.lotto.infrastructure.auth;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Log4j2
public class AuthController {

    @GetMapping("/auto-login")
    public ResponseEntity<?> autoLogin() {
        log.info("--TRY AUTO LOGIN USER");
        return null;
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

    @GetMapping("/login")
    public ResponseEntity<?> login() {
        log.info("--TRY LOGIN USER");
        return null;
    }

    @GetMapping("/register")
    public ResponseEntity<?> register() {
        log.info("--TRY REGISTER USER");
        return null;
    }
}