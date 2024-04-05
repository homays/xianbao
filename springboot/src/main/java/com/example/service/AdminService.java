package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.vo.req.RegisterDTO;

import java.util.List;

/**
 * 管理员业务处理
 **/
public interface AdminService extends IService<Admin> {

    Result add(Admin admin);

    Result deleteById(Integer id);

    Result deleteBatch(List<Integer> ids);

    Result updateAdmin(Admin admin);

    Result selectById(Integer id);

    Result selectAll(Admin admin);

    Result selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Result login(Account account);

    Result register(RegisterDTO registerDTO);

    Result updatePassword(Account account);
}