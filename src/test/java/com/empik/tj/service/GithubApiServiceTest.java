package com.empik.tj.service;

import com.empik.tj.model.helper.GithubUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GithubApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubApiService githubApiService = new GithubApiService();

    @Test
    void getGithubUser() {
        GithubUser user = new GithubUser(583231L, "octocat", "The Octocat",
                "User", "https://avatars.githubusercontent.com/u/583231?v=4",
                "2011-01-25T18:44:36Z", 10872L, 8L);
        ReflectionTestUtils.setField(githubApiService, GithubApiService.class,
                "uri", "https://api.github.com/users/", String.class);
        when(restTemplate.getForObject(
                "https://api.github.com/users/octocat", GithubUser.class)).thenReturn(user);

        GithubUser githubUser = githubApiService.getGithubUser("octocat");

        Assertions.assertEquals(user, githubUser);
    }

    @Test
    void getGithubUserError() {
        ReflectionTestUtils.setField(githubApiService, GithubApiService.class,
                "uri", "https://api.github.com/users/", String.class);
        when(restTemplate.getForObject(
                "https://api.github.com/users/octocat", GithubUser.class)).thenThrow(HttpClientErrorException.class);

        try {
            githubApiService.getGithubUser("octocat");
        } catch (HttpClientErrorException e) {
            assertEquals(e.getClass(), HttpClientErrorException.class);
        }
    }
}