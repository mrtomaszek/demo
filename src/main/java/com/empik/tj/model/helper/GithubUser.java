package com.empik.tj.model.helper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubUser {

    Long id;
    String login;
    String name;
    String type;
    String avatar_url;
    String created_at;
    Long followers;
    Long public_repos;

}
