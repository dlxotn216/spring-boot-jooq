package me.taesu.springjooq.user.application;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
public class UserEmailDuplicatedException extends NestedRuntimeException {
    public UserEmailDuplicatedException(String msg) {
        super(msg);
    }

    public UserEmailDuplicatedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public static UserEmailDuplicatedException from(String email) {
        return new UserEmailDuplicatedException(String.format("User email [%s] duplicated", email));
    }
}
