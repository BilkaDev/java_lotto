package pl.lotto.infrastructure.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.lotto.domain.common.Code;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;

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


    public ResponseEntity<?> loginByToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDto(Code.A3));
    }

}
