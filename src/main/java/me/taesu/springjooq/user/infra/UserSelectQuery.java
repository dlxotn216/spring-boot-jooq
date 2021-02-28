package me.taesu.springjooq.user.infra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Getter @NoArgsConstructor @Setter
public class UserSelectQuery {
    private Long key;
    private String name;
    private String email;

    public UserSelectQuery(Long key, String name, String email) {
        this.key = key;
        this.name = name;
        this.email = email;
    }
}
