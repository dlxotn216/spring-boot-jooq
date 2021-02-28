package me.taesu.springjooq.user.application;

import lombok.RequiredArgsConstructor;
import me.taesu.springjooq.base.EntityNotExistsException;
import me.taesu.springjooq.user.domain.User;
import me.taesu.springjooq.user.domain.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Component @RequiredArgsConstructor
public class UserRetrieveService {
    private final UserRepository userRepository;

    public User retrieveByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotExistsException::new);
    }
}
