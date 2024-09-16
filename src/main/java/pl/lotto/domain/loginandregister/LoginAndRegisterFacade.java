package pl.lotto.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.domain.loginandregister.dto.RegistrationResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

@AllArgsConstructor
@Component
public class LoginAndRegisterFacade implements ILoginAndRegisterFacade {
    private final UserRepository userRepository;

    @Override
    public UserDto findByLogin(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public RegistrationResultDto register(RegisterUserDto registerUserDto) {
        final User user = User.builder()
                .login(registerUserDto.login())
                .password(registerUserDto.password())
                .email(registerUserDto.email()).build();
        User saved = userRepository.save(user);

        return RegistrationResultDto.builder()
                .created(true)
                .id(saved.getId())
                .login(saved.getLogin())
                .email(saved.getEmail())
                .build();
    }
}
