package pl.lotto.domain.loginandregister;

import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.domain.loginandregister.dto.RegistrationResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

public interface ILoginAndRegisterFacade {
    UserDto findByLogin(String login);

    RegistrationResultDto register(RegisterUserDto registerUserDto);

}
