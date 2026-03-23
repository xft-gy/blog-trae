package com.blog.application.user.service;

import com.blog.application.user.dto.*;
import com.blog.common.constant.CommonConstants;
import com.blog.common.exception.BusinessException;
import com.blog.common.util.JwtUtil;
import com.blog.domain.user.entity.User;
import com.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setNickname(request.getUsername());
        user.setStatus(CommonConstants.UserStatus.ENABLED);
        user.setRole(CommonConstants.UserRole.USER);

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid username or password");
        }

        if (CommonConstants.UserStatus.DISABLED.equals(user.getStatus())) {
            throw new BusinessException("Account has been disabled");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getAvatar(),
                user.getRole()
        );
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("User not found"));
    }

    public UserDTO getUserDTOById(Long userId) {
        User user = getUserById(userId);
        return convertToDTO(user);
    }

    public void updateUser(Long userId, UpdateUserRequest request) {
        User user = getUserById(userId);
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getAvatar() != null) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        userRepository.save(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void updateUserRole(Long userId, String role) {
        if (!CommonConstants.UserRole.USER.equals(role) && !CommonConstants.UserRole.ADMIN.equals(role)) {
            throw new BusinessException("Invalid role");
        }
        User user = getUserById(userId);
        user.setRole(role);
        userRepository.save(user);
    }

    public void updateUserStatus(Long userId, Integer status) {
        if (!CommonConstants.UserStatus.DISABLED.equals(status) && !CommonConstants.UserStatus.ENABLED.equals(status)) {
            throw new BusinessException("Invalid status");
        }
        User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setNickname(user.getNickname());
        dto.setAvatar(user.getAvatar());
        dto.setBio(user.getBio());
        dto.setStatus(user.getStatus());
        dto.setRole(user.getRole());
        dto.setCreatedTime(user.getCreatedTime());
        dto.setUpdatedTime(user.getUpdatedTime());
        return dto;
    }
}
