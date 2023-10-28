package com.empik.tj.service;

import com.empik.tj.model.db.UserRequestEntity;
import com.empik.tj.model.dto.ResponseDto;
import com.empik.tj.model.helper.GithubUser;
import com.empik.tj.repository.UserRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private String username;
    private GithubUser user;
    private UserRequestEntity entity;
    private ResponseDto dto;

    @Mock
    GithubApiService githubApiService;

    @Mock
    UserRequestRepository userRequestRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void prepareData() {
        username = "octocat";

        user = new GithubUser(583231L, "octocat", "The Octocat",
                "User", "https://avatars.githubusercontent.com/u/583231?v=4",
                "2011-01-25T18:44:36Z", 10872L, 8L);
        entity = new UserRequestEntity("octocat", 1L);
        dto = ResponseDto.builder()
                .id("583231")
                .login("octocat")
                .name("The Octocat")
                .type("User")
                .avatarUrl("https://avatars.githubusercontent.com/u/583231?v=4")
                .createdAt("2011-01-25T18:44:36Z")
                .calculations("0.005518763796909493")
                .build();
    }

    @Test
    void getGithubUser() {

        when(githubApiService.getGithubUser("octocat")).thenReturn(user);

        when(userRequestRepository.findById(username)).thenReturn(Optional.of(entity));

        ResponseDto responseDto = userService.getGithubUser(username);

        assertEquals(dto, responseDto);
    }

    @Test
    void getGithubUserFirstTime() {

        when(githubApiService.getGithubUser("octocat")).thenReturn(user);

        when(userRequestRepository.findById(username)).thenReturn(Optional.empty());

        ResponseDto responseDto = userService.getGithubUser(username);

        assertEquals(dto, responseDto);
    }

    @Test
    void getGithubUserError() {
        when(githubApiService.getGithubUser("aaaa")).thenThrow(HttpClientErrorException.class);

        try {
            userService.getGithubUser("aaaa");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getClass(), HttpClientErrorException.class);
        }
    }
}