package com.empik.tj.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseDto {

    String id;
    String login;
    String name;
    String type;
    String avatarUrl;
    String createdAt;
    String calculations;
}
