package me.taesu.springjooq.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Table(name = "HOM_USER")
@Entity @NoArgsConstructor @Getter @Builder
public class User {

    @Id
    @Column(name = "USER_KEY")
    @GeneratedValue(generator = "USER_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ")
    private Long key;

    @Column(name = "USER_NAME", nullable = false)
    public String name;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    public User(Long key, String name, String email) {
        this.key = key;
        this.name = name;
        this.email = email;
    }
}
