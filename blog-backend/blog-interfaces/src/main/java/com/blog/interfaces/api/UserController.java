package com.blog.interfaces.api;

import com.blog.application.user.dto.*;
import com.blog.application.user.service.UserService;
import com.blog.common.annotation.RequireAdmin;
import com.blog.common.annotation.RequireLogin;
import com.blog.common.constant.CommonConstants;
import com.blog.common.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @RequireLogin
    public Result<UserDTO> getCurrentUserProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(CommonConstants.USER_ID_KEY);
        UserDTO userDTO = userService.getUserDTOById(userId);
        return Result.success(userDTO);
    }

    @PutMapping("/profile")
    @RequireLogin
    public Result<Void> updateCurrentUserProfile(HttpServletRequest request, @RequestBody UpdateUserRequest updateUserRequest) {
        Long userId = (Long) request.getAttribute(CommonConstants.USER_ID_KEY);
        userService.updateUser(userId, updateUserRequest);
        return Result.success();
    }

    @GetMapping
    @RequireAdmin
    public Result<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return Result.success(users);
    }

    @GetMapping("/{userId}")
    @RequireAdmin
    public Result<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserDTOById(userId);
        return Result.success(userDTO);
    }

    @PutMapping("/{userId}/role")
    @RequireAdmin
    public Result<Void> updateUserRole(@PathVariable Long userId, @RequestBody UpdateUserRoleRequest request) {
        userService.updateUserRole(userId, request.getRole());
        return Result.success();
    }

    @PutMapping("/{userId}/status")
    @RequireAdmin
    public Result<Void> updateUserStatus(@PathVariable Long userId, @RequestBody UpdateUserStatusRequest request) {
        userService.updateUserStatus(userId, request.getStatus());
        return Result.success();
    }

    @DeleteMapping("/{userId}")
    @RequireAdmin
    public Result<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return Result.success();
    }
}
