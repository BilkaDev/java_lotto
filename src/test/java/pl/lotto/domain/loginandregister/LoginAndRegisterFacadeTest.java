package pl.lotto.domain.loginandregister;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.lotto.domain.loginandregister.dto.RegisterUserDto;
import pl.lotto.domain.loginandregister.dto.RegistrationResultDto;
import pl.lotto.domain.loginandregister.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginAndRegisterFacadeTest {
    ILoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new InMemoryUserRepository()
    );


    @Test
    public void should_register_user() {
        // given
        RegisterUserDto user = RegisterUserDto.builder()
                .login("login")
                .password("password")
                .email("email")
                .build();

        // when
        RegistrationResultDto registrationResultDto = loginAndRegisterFacade.register(user);

        // then
        assertAll(
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.login()).isEqualTo("login"),
                () -> assertThat(registrationResultDto.email()).isEqualTo("email")
        );
    }

    @Test
    public void should_find_user_by_login() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("login", "password", "email");
        RegistrationResultDto register = loginAndRegisterFacade.register(registerUserDto);

        // when
        UserDto userByName = loginAndRegisterFacade.findByLogin(register.login());

        // then
        assertThat(userByName).isEqualTo(
                UserDto.builder()
                        .login("login")
                        .email("email")
                        .password("password")
                        .id(register.id())
                        .build()
        );
    }

    @Test
    public void should_throw_exception_when_user_not_found() {
        // given
        String login = "someUser";

        // when
        Throwable thrown = catchThrowable(() -> loginAndRegisterFacade.findByLogin(login));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }
}