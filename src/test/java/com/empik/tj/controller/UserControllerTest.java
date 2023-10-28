package com.empik.tj.controller;

import com.empik.tj.model.dto.ResponseDto;
import com.empik.tj.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    String login = "octocat";
    String userPath = "/user/octocat";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    void getUserFromGithub() throws Exception {
        ResponseDto dto = ResponseDto.builder()
                .id("583231")
                .login("octocat")
                .name("The Octocat")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt("2011-01-25T18:44:36Z")
                .calculations("0.005518763796909493")
                .build();
        Gson gson = new Gson();
        String jsonDto = gson.toJson(dto);
        when(service.getGithubUser(login)).thenReturn(dto);
        this.mockMvc.perform(get(userPath)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(jsonDto));
    }

    @Test
    void getUserFromGithubUserNotFound() throws Exception {
        when(service.getGithubUser(login)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "404"));
        this.mockMvc.perform(get(userPath)).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("User not found")));
    }

    @Test
    void getUserFromGithubUserServerError() throws Exception {
        when(service.getGithubUser(login)).thenThrow(new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, "500"));
        this.mockMvc.perform(get(userPath)).andDo(print()).andExpect(status().isServiceUnavailable())
                .andExpect(content().string(containsString("Server error")));
    }
}