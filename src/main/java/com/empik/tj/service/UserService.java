package com.empik.tj.service;

import com.empik.tj.model.db.UserRequestEntity;
import com.empik.tj.model.dto.ResponseDto;
import com.empik.tj.model.helper.GithubUser;
import com.empik.tj.repository.UserRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRequestRepository userRequestRepository;
    private final GithubApiService githubApiService;

    public ResponseDto getGithubUser(String login) {

        GithubUser githubUser = githubApiService.getGithubUser(login);
        updateRequestCount(login);

        return prepareResponseDto(githubUser);
    }

    private void updateRequestCount(String login) {
        Optional<UserRequestEntity> optionalUserRequestEntity = userRequestRepository.findById(login);
        if (optionalUserRequestEntity.isPresent()) {
            UserRequestEntity userRequestEntity = optionalUserRequestEntity.get();
            userRequestEntity.setRequestCount(userRequestEntity.getRequestCount() + 1);
            userRequestRepository.save(userRequestEntity);
        } else {
            userRequestRepository.save(new UserRequestEntity(login, 1L));
        }
    }

    private ResponseDto prepareResponseDto(GithubUser user) {
        String calculationResult = user.getFollowers() != 0 ?
                Double.toString((double) 6 / user.getFollowers() * (2 + user.getPublic_repos())) : "0";
        return ResponseDto.builder()
                .id(user.getId().toString())
                .login(user.getLogin())
                .name(user.getName())
                .type(user.getType())
                .avatarUrl(user.getAvatar_url())
                .createdAt(user.getCreated_at())
                .calculations(calculationResult)
                .build();
    }

}
