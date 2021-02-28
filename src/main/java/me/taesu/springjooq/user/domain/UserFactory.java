package me.taesu.springjooq.user.domain;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
public class UserFactory {
    private UserFactory() {
    }

    public static User from(String name, String email) {
        return User.builder().name(name).email(email).build();
    }
}
