package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goods;
import com.example.vo.req.GoodsQueryDTO;

import java.util.List;


public interface GoodsService extends IService<Goods> {

    void add(Goods goods);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    Goods selectById(Integer id);

    List<Goods> selectAll(Goods goods);

    Page<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize);

    Page<Goods> selectFrontPage(GoodsQueryDTO goodsQueryDTO, Goods goods);

    void like(Integer goodsId);

    void collect(Integer goodsId);

    void updateByGoods(Goods goods);
}
