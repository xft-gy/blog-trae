package com.blog.common.constant;

public interface CommonConstants {

    String AUTHORIZATION_HEADER = "Authorization";
    String BEARER_PREFIX = "Bearer ";
    String USER_ID_KEY = "userId";
    String USERNAME_KEY = "username";
    String ROLE_KEY = "role";

    interface UserStatus {
        Integer DISABLED = 0;
        Integer ENABLED = 1;
    }

    interface UserRole {
        String USER = "USER";
        String ADMIN = "ADMIN";
    }

    interface ArticleStatus {
        Integer DRAFT = 0;
        Integer PUBLISHED = 1;
        Integer OFFLINE = 2;
    }

    interface CommentStatus {
        Integer PENDING = 0;
        Integer APPROVED = 1;
        Integer REJECTED = 2;
    }
}
