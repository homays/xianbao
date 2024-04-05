package com.example.service;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.req.RegisterDTO;

import java.util.List;

public interface UserService extends IService<User> {

    void add(User user);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateUser(User user);

    Object selectById(Integer id);

    Object selectAll(User user);

    Object selectPage(User user, Integer pageNum, Integer pageSize);

    Result login(Account account);

    void register(RegisterDTO registerDTO);
}
