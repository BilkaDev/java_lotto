package pl.lotto.domain.loginandregister;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document
@AllArgsConstructor
@Getter
public class User {
    @Id
    @Indexed(unique = true)
    private String id;
    private String login;
    private String password;
    private String email;


}
