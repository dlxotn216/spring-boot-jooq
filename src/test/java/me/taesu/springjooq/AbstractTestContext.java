package me.taesu.springjooq;

import me.taesu.springjooq.base.ExceptionHandlerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by itaesu on 2020/12/29.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class AbstractTestContext {

    protected MockMvc mockMvc;

    abstract protected Object controller();

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller())
                                 .addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
                                 .setControllerAdvice(ExceptionHandlerAdvice.class)
                                 .alwaysDo(print())
                                 .build();
    }

}
