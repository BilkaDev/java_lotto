package pl.lotto.domain.loginandregister.dto;

import lombok.Builder;

@Builder
public record UserDto(String id, String login, String password, String email) {

}
