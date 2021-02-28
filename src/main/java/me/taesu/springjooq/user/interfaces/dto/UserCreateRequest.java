package me.taesu.springjooq.user.interfaces.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@NoArgsConstructor @Getter @Setter
public class UserCreateRequest {
    private String name;
    private String email;
}
