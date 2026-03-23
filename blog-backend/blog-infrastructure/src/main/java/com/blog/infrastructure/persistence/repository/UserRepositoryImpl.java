package com.blog.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.user.entity.User;
import com.blog.domain.user.repository.UserRepository;
import com.blog.infrastructure.persistence.entity.UserDO;
import com.blog.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        if (userDO.getId() == null) {
            userMapper.insert(userDO);
        } else {
            userMapper.updateById(userDO);
        }
        BeanUtils.copyProperties(userDO, user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if (userDO == null) {
            return Optional.empty();
        }
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        UserDO userDO = userMapper.selectOne(wrapper);
        if (userDO == null) {
            return Optional.empty();
        }
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getEmail, email);
        UserDO userDO = userMapper.selectOne(wrapper);
        if (userDO == null) {
            return Optional.empty();
        }
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return Optional.of(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getEmail, email);
        return userMapper.selectCount(wrapper) > 0;
    }

    @Override
    public List<User> findAll() {
        List<UserDO> userDOList = userMapper.selectList(null);
        return userDOList.stream().map(userDO -> {
            User user = new User();
            BeanUtils.copyProperties(userDO, user);
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public void delete(User user) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        userMapper.deleteById(userDO.getId());
    }
}
