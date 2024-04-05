package com.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.common.Constants.USER_DEFAULT_PASSWORD;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void add(User user) {
        //  1. 校验你的用户名是否重复
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(dbUser)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        // 设置名称
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        // 设置默认密码
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword(USER_DEFAULT_PASSWORD);
        }
        user.setRole(RoleEnum.USER.name());
        userMapper.insert(user);
    }

    @Override
    public void deleteById(Integer id) {
        deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        deleteBatch(ids);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    @Override
    public Object selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public Object selectAll(User user) {
        return userMapper.selectAll(user);
    }

    @Override
    public Object selectPage(User user, Integer pageNum, Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (user.getId() != null) {
            wrapper.eq(User::getId, user.getId());
        }
        if (user.getUsername() != null) {
            wrapper.like(User::getUsername, user.getUsername());
        }
        if (user.getName() != null) {
            wrapper.eq(User::getName, user.getName());
        }
        if (user.getPhone() != null) {
            wrapper.eq(User::getPhone, user.getPhone());
        }
        if (user.getEmail() != null) {
            wrapper.eq(User::getEmail, user.getEmail());
        }
        if (user.getRole() != null) {
            wrapper.eq(User::getRole, user.getRole());
        }
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        return Result.success(userPage);
    }
}




