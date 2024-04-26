package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.RoleEnum;
import com.example.entity.Address;
import com.example.entity.User;
import com.example.mapper.AddressMapper;
import com.example.mapper.UserMapper;
import com.example.service.AddressService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService{

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Address address) {
        String userId = ThreadLocalUtil.getUserId();
        address.setUserId(Integer.parseInt(userId));
        addressMapper.insert(address);
    }

    @Override
    public void deleteById(Integer id) {
        addressMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        addressMapper.deleteBatchIds(ids);
    }

    @Override
    public Address selectById(Integer id) {
        return addressMapper.selectById(id);
    }

    @Override
    public List<Address> selectAll(Address address) {
        return addressMapper.selectList(null);
    }

    @Override
    public Page<Address> selectPage(Address address, Integer pageNum, Integer pageSize) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String role = claims.get("role").toString();
        if (RoleEnum.USER.name().equals(role)) {
            String userId = claims.get("userId").toString();
            address.setUserId(Integer.parseInt(userId));
        }
        Page<Address> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(address.getUserId() != null, Address::getUserId, address.getUserId());
        wrapper.like(StrUtil.isNotBlank(address.getName()), Address::getName, address.getName());
        wrapper.like(StrUtil.isNotBlank(address.getAddress()), Address::getAddress, address.getAddress());
        Page<Address> addressPage = addressMapper.selectPage(page, wrapper);
        List<Address> records = addressPage.getRecords();
        List<Address> collect = records.stream().peek(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getName());
        }).collect(Collectors.toList());
        addressPage.setRecords(collect);
        return addressPage;
    }
}




