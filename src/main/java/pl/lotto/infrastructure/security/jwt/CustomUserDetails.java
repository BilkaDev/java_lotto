package pl.lotto.infrastructure.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


class CustomUserDetails extends User {
    private final String email;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String email) {
        super(username, password, authorities);
        this.email = email;
    }


    public String getEmail() {
        return email;
    }
}
