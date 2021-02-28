package me.taesu.springjooq.base;

import org.springframework.core.NestedRuntimeException;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
public class EntityNotExistsException extends NestedRuntimeException {
    public EntityNotExistsException() {
        super("Entity not exists");
    }
}
