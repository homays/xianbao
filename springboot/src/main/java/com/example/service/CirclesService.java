package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Circles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CirclesService extends IService<Circles> {

    void add(Circles circles);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Circles selectById(Integer id);

    List<Circles> selectAll(Circles circles);

    Page<Circles> selectPage(Circles circles, Integer pageNum, Integer pageSize);
}
