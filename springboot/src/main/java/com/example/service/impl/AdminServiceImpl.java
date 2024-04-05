package com.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.Constants;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import com.example.utils.JwtUtil;
import com.example.vo.req.RegisterDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author Arrebol
 * @date 2024/3/14
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 新增
     */
    @Override
    public Result add(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole(RoleEnum.ADMIN.name());
        adminMapper.insert(admin);
        return Result.success();
    }

    /**
     * 删除
     */
    @Override
    public Result deleteById(Integer id) {
        adminMapper.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @Override
    public Result deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            adminMapper.deleteById(id);
        }
        return Result.success();
    }

    /**
     * 修改
     */
    @Override
    public Result updateAdmin(Admin admin) {
        adminMapper.updateAdmin(admin);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @Override
    public Result selectById(Integer id) {
        return Result.success(adminMapper.selectById(id));
    }

    /**
     * 查询所有
     */
    @Override
    public Result selectAll(Admin admin) {
        return Result.success(adminMapper.selectAll(admin));
    }

    /**
     * 分页查询
     */
    @Override
    public Result selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if (admin.getId() != null) {
            wrapper.eq(Admin::getId, admin.getId());
        }
        if (admin.getUsername() != null) {
            wrapper.like(Admin::getUsername, admin.getUsername());
        }
        if (admin.getName() != null) {
            wrapper.eq(Admin::getName, admin.getName());
        }
        if (admin.getPhone() != null) {
            wrapper.eq(Admin::getPhone, admin.getPhone());
        }
        if (admin.getEmail() != null) {
            wrapper.eq(Admin::getEmail, admin.getEmail());
        }
        if (admin.getRole() != null) {
            wrapper.eq(Admin::getRole, admin.getRole());
        }
        Page<Admin> adminPage = adminMapper.selectPage(page, wrapper);
        return Result.success(adminPage);
    }

    /**
     * 登录
     */
    @Override
    public Result login(Account account) {
        Account dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", dbAdmin.getId());
        claims.put("role", RoleEnum.ADMIN.name());
        String token = JwtUtil.genToken(claims);

        dbAdmin.setToken(token);
        return Result.success(dbAdmin);
    }

    /**
     * 注册
     */
    @Override
    public Result register(RegisterDTO registerDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(registerDTO, admin);
        add(admin);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @Override
    public Result updatePassword(Account account) {
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbAdmin.setPassword(account.getNewPassword());
        adminMapper.updateById(dbAdmin);
        return Result.success();
    }

}
