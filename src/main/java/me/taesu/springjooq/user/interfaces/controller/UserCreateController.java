package me.taesu.springjooq.user.interfaces.controller;

import lombok.RequiredArgsConstructor;
import me.taesu.springjooq.base.SuccessResponse;
import me.taesu.springjooq.user.application.UserCreateService;
import me.taesu.springjooq.user.interfaces.dto.UserCreateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@RestController @RequiredArgsConstructor
public class UserCreateController {

    private final UserCreateService userCreateService;

    @PostMapping("/api/v1/users")
    public ResponseEntity<SuccessResponse> create(@RequestBody UserCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.from(userCreateService.create(request))
        );
    }

}
