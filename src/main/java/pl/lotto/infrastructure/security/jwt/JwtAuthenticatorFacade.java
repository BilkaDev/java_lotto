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
import pl.lotto.infrastructure.security.jwt.dto.JwtResponseDto;
import pl.lotto.infrastructure.security.jwt.dto.ResponseDto;

@AllArgsConstructor
@Component
public class JwtAuthenticatorFacade {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtResponseDto authenticate(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.login(), loginRequest.password())
        );
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtService.createToken(user);
        return JwtResponseDto.builder()
                .token(token)
                .email(user.getEmail())
                .login(user.getUsername())
                .tokenExp(jwtService.getExpiration())
                .build();
    }

    public ResponseEntity<?> loginByToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDto(Code.A3));
    }


}
