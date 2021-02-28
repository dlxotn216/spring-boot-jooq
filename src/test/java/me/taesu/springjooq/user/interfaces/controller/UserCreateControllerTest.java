package me.taesu.springjooq.user.interfaces.controller;

import lombok.extern.slf4j.Slf4j;
import me.taesu.springjooq.AbstractTestContext;
import me.taesu.springjooq.user.application.UserRetrieveService;
import me.taesu.springjooq.user.domain.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by itaesu on 2020/12/28.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Slf4j
class UserCreateControllerTest extends AbstractTestContext {

    @SpyBean
    private UserRetrieveService userRetrieveService;

    @Autowired
    private UserCreateController userCreateController;

    @Override
    protected Object controller() {
        return userCreateController;
    }

    @DisplayName("사용자 생성 성공")
    @Test
    void shouldSuccessToCreateUser() throws Exception {
        //given
        final var name = "Taesu Lee";
        final var email = "taesu@crscube.co.kr";

        //when
        final MockHttpServletRequestBuilder request
                = post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"name\": \"" + name + "\",\n" +
                                 "  \"email\": \"" + email + "\"\n" +
                                 "}")
                .accept(MediaType.APPLICATION_JSON);

        //then
        this.mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.result.userKey").exists())
                    .andDo(print())
                    .andReturn();
    }

    @DisplayName("사용자 생성 실패 (중복된 이메일)")
    @Test
    void shouldFailToCreateUser_causeDuplicatedException() throws Exception {
        //given
        final var name = "Taesu Lee";
        final var email = "taesu@crscube.co.kr";

        final var exists = UserFactory.from("test", email);
        doReturn(exists).when(userRetrieveService).retrieveByEmail(email);

        //when
        final MockHttpServletRequestBuilder request
                = post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                                 "  \"name\": \"" + name + "\",\n" +
                                 "  \"email\": \"" + email + "\"\n" +
                                 "}")
                .accept(MediaType.APPLICATION_JSON);

        //then
        this.mockMvc.perform(request)
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value("fail"))
                    .andDo(print())
                    .andReturn();
    }
}