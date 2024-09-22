package pl.lotto.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lotto.domain.loginandregister.LoginAndRegisterFacade;
import pl.lotto.domain.loginandregister.dto.UserDto;

import java.util.Collections;

@AllArgsConstructor
class LoginUserDetailsService implements UserDetailsService {
    private final LoginAndRegisterFacade loginAndRegisterFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = loginAndRegisterFacade.findByLogin(username);
        return this.getUser(userDto);
    }

    private CustomUserDetails getUser(UserDto user) {
        return new CustomUserDetails(
                user.login(),
                user.password(),
                Collections.emptyList(),
                user.email()
        );
    }
}
