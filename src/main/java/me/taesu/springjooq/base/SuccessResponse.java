package me.taesu.springjooq.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.taesu.springjooq.user.interfaces.dto.UserRetrieveResponse;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Getter @NoArgsConstructor
public class SuccessResponse {
    private final String status = "success";
    private Object result;
    private String message;

    public SuccessResponse(Object result, String message) {
        this.result = result;
        this.message = message;
    }

    public static SuccessResponse from(Object result) {
        return from(result, "Request was success");
    }

    public static SuccessResponse from(Object result, String message) {
        return new SuccessResponse(result, message);
    }
}
