package com.blog.application.user.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private String avatar;
    private String bio;
    private Integer status;
    private String role;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
