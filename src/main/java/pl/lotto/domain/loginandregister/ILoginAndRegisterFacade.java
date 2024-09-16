package pl.lotto.domain.loginandregister;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.domain.loginandregister.dto.RegistrationResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

public interface ILoginAndRegisterFacade {
    UserDto findByLogin(String login) throws UsernameNotFoundException;

    RegistrationResultDto register(RegisterUserDto registerUserDto);

}
