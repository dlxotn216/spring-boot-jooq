package me.taesu.springjooq.user.infra;

import lombok.RequiredArgsConstructor;
import me.taesu.springjooq.Tables;
import me.taesu.springjooq.tables.records.HomUserRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Component @RequiredArgsConstructor
public class UserQuery {

    private final DSLContext dslContext;

    public List<UserSelectQuery> selectUsers() {
        final var mstUserRecords = dslContext
                .selectFrom(Tables.HOM_USER)
                .fetchInto(HomUserRecord.class);
        //
        //
        return mstUserRecords.stream()
                             .map(record -> new UserSelectQuery(
                                     record.getUserKey(),
                                     record.getUserName(),
                                     record.getUserEmail()
                             ))
                             .collect(Collectors.toList());

    }

}
