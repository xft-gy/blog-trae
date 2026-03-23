package com.blog.application.user.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String nickname;
    private String avatar;
    private String bio;
}
