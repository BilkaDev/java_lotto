package pl.lotto.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.lotto.infrastructure.auth.LoginRequestDto;

@AllArgsConstructor
@Component
public class JwtAuthenticatorFacade {
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> authenticate(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password())
        );
        return null;
    }
}
