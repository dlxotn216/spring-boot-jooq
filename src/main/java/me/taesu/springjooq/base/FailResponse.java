package me.taesu.springjooq.base;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Getter @NoArgsConstructor
public class FailResponse {
    private final String status = "fail";
    private String message;

    public FailResponse(String message) {
        this.message = message;
    }

    public static FailResponse from(String message) {
        return new FailResponse(message);
    }
}
