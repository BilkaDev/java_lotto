package pl.lotto.infrastructure.security.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.lotto.domain.loginandregister.ILoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.UserDto;
import pl.lotto.infrastructure.auth.dto.LoginRequestDto;
import pl.lotto.infrastructure.security.jwt.dto.JwtResponseDto;

@AllArgsConstructor
@Component
@Log4j2
public class JwtAuthenticatorFacade {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ILoginAndRegisterFacade loginAndRegisterFacade;

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

    public JwtResponseDto loginByToken(String token) throws TokenExpiredException {
        try {
            DecodedJWT decodedJWT = jwtService.validateToken(token);
            String login = decodedJWT.getSubject();
            UserDto userDto = loginAndRegisterFacade.findByLogin(login);
            return JwtResponseDto.builder()
                    .token(decodedJWT.getToken())
                    .email(userDto.email())
                    .tokenExp(decodedJWT.getExpiresAt().getTime())
                    .login(userDto.login())
                    .build();
        } catch (
                JWTVerificationException | IllegalArgumentException e) {
            throw new TokenExpiredException();
        }
    }

    public boolean loggedIn(String token) {
        try {
            jwtService.validateToken(token);
            return true;
        } catch (TokenExpiredException e) {
            return false;
        }
    }
}
