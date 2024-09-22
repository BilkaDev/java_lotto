package pl.lotto.infrastructure.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.*;


@Service
@AllArgsConstructor
@Log4j2
public class JwtService {
    private final Clock clock;
    private final JwtConfigurationProperties properties;

    private DecodedJWT verifyToken(final String token) throws IllegalArgumentException, JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(properties.secret());
        JWTVerifier.BaseVerification verification = (JWTVerifier.BaseVerification) JWT.require(algorithm)
                .acceptLeeway(1)
                .acceptExpiresAt(5).withIssuer(properties.issuer());
        JWTVerifier verifier = verification.build(clock);

        return verifier.verify(token);
    }

    public String createToken(CustomUserDetails user) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = now.plus(Duration.ofDays(properties.expirationDays()));
        String issuer = properties.issuer();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public Long getExpiration() {
        return this.properties.expirationDays();
    }


    public DecodedJWT validateToken(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = verifyToken(token);
        } catch (JWTVerificationException | IllegalArgumentException e) {
            log.info("Can't login because token expired");
            throw new TokenExpiredException();
        }

        return decodedJWT;
    }

}