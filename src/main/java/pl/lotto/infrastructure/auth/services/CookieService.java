package pl.lotto.infrastructure.auth.services;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
class CookieService {
    public Cookie generateCookie(final String name, final String value, int exp) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(exp);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public Cookie removeCookie(final Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setHttpOnly(true);
                return cookie;
            }
        }
        return null;
    }
}
