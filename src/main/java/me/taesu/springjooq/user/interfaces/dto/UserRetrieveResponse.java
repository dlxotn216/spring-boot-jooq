package me.taesu.springjooq.user.interfaces.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.taesu.springjooq.user.domain.User;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@NoArgsConstructor @Getter @Setter
public class UserRetrieveResponse {
    private Long userKey;
    private String name;
    private String email;

    private UserRetrieveResponse(Long userKey, String name, String email) {
        this.userKey = userKey;
        this.name = name;
        this.email = email;
    }

    public static UserRetrieveResponse from(User user) {
        return new UserRetrieveResponse(user.getKey(), user.getName(), user.getEmail());
    }
}
