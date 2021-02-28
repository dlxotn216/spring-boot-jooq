package me.taesu.springjooq.user.application;

import lombok.RequiredArgsConstructor;
import me.taesu.springjooq.base.EntityNotExistsException;
import me.taesu.springjooq.user.domain.User;
import me.taesu.springjooq.user.domain.UserFactory;
import me.taesu.springjooq.user.domain.UserRepository;
import me.taesu.springjooq.user.interfaces.dto.UserCreateRequest;
import me.taesu.springjooq.user.interfaces.dto.UserRetrieveResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Component @RequiredArgsConstructor
public class UserCreateService {
    private final UserRetrieveService userRetrieveService;
    private final UserRepository userRepository;

    @Transactional
    public UserRetrieveResponse create(UserCreateRequest request) {
        try {
            userRetrieveService.retrieveByEmail(request.getEmail());
            throw UserEmailDuplicatedException.from(request.getEmail());
        } catch (EntityNotExistsException e) {
            // ignore
        }

        final var user = userRepository.save(UserFactory.from(request.getName(), request.getEmail()));
        return UserRetrieveResponse.from(user);
    }
}
