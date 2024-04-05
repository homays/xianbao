package com.example.service;

import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {

    void add(User user);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateUser(User user);

    Object selectById(Integer id);

    Object selectAll(User user);

    Object selectPage(User user, Integer pageNum, Integer pageSize);
}
