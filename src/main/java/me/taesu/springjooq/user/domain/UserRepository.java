package me.taesu.springjooq.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
