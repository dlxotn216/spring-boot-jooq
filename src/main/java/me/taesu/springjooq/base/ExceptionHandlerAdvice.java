package me.taesu.springjooq.base;

import me.taesu.springjooq.user.application.UserEmailDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@RestController
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserEmailDuplicatedException.class)
    public ResponseEntity<FailResponse> handleUserEmailDuplicatedException(UserEmailDuplicatedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(FailResponse.from(e.getMessage()));

    }

}
