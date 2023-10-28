package com.empik.tj.service;


import com.empik.tj.model.helper.GithubUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GithubApiService {

    @Value("${githubapi}")
    private String uri;

    public GithubUser getGithubUser(String login) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri + login, GithubUser.class);
    }
}
