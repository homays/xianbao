package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AddressService extends IService<Address> {

    void add(Address address);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Address selectById(Integer id);

    List<Address> selectAll(Address address);

    Page<Address> selectPage(Address address, Integer pageNum, Integer pageSize);
}
