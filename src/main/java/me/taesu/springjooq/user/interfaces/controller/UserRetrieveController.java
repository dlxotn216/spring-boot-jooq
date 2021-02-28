package me.taesu.springjooq.user.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.taesu.springjooq.user.infra.UserQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@RestController @RequiredArgsConstructor
public class UserRetrieveController {

    private final UserQuery userQuery;

    @GetMapping("/api/v1/users")
    public ResponseEntity<Object> getUsers() {
        return ResponseEntity.ok(userQuery.selectUsers());
    }
}
