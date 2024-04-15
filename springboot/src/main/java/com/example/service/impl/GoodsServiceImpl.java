package com.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.entity.User;
import com.example.mapper.GoodsMapper;
import com.example.mapper.UserMapper;
import com.example.service.GoodsService;
import com.example.vo.req.GoodsQueryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        goodsMapper.deleteBatchIds(ids);
    }

    @Override
    public Goods selectById(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        User user = userMapper.selectById(goods.getUserId());
        goods.setUserName(user.getName());
        return goods;
    }

    @Override
    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    @Override
    public Page<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        Page<Goods> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(goods.getName()), Goods::getName, goods.getName());
        wrapper.like(StrUtil.isNotBlank(goods.getContent()), Goods::getContent, goods.getContent());
        wrapper.like(StrUtil.isNotBlank(goods.getAddress()), Goods::getAddress, goods.getAddress());
        wrapper.like(StrUtil.isNotBlank(goods.getCategory()), Goods::getContent, goods.getCategory());
        Page<Goods> goodsPage = goodsMapper.selectPage(page, wrapper);
        List<Goods> records = goodsPage.getRecords();
        List<Goods> collect = records.stream().map(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getName());
            return item;
        }).collect(Collectors.toList());
        goodsPage.setRecords(collect);
        return goodsPage;
    }


    @Override
    public Page<Goods> selectFrontPage(GoodsQueryDTO goodsQueryDTO, Goods goods) {
        String sort = goodsQueryDTO.getSort();
        Page<Goods> page = new Page<>(goodsQueryDTO.getPageNum(), goodsQueryDTO.getPageSize());
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        // wrapper.like(StrUtil.isNotBlank(goods.getName()), Goods::getName, goods.getName());
        wrapper.like(StrUtil.isNotBlank(goodsQueryDTO.getCategory()), Goods::getCategory, goodsQueryDTO.getCategory());
        if (StrUtil.isNotBlank(sort) && sort.equals("最新")) {
            wrapper.orderByDesc(StrUtil.isNotBlank(String.valueOf(goods.getId())), Goods::getId);
        } else if (StrUtil.isNotBlank(sort) && sort.equals("最热")) {
            wrapper.orderByDesc(StrUtil.isNotBlank(String.valueOf(goods.getReadCount())), Goods::getReadCount);
        }
        return goodsMapper.selectPage(page, wrapper);
    }
}




