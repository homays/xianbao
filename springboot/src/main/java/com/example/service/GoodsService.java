package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface GoodsService extends IService<Goods> {

    void add(Goods goods);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Goods selectById(Integer id);

    List<Goods> selectAll(Goods goods);

    Page<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize);
}
