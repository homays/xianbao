package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.GoodsService;
import com.example.mapper.GoodsMapper;
import com.example.vo.req.GoodsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{

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
        return goodsMapper.selectById(id);
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
            User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                    .eq(User::getId, item.getUserId()));
            item.setUserName(user.getName());
            return item;
        }).collect(Collectors.toList());
        goodsPage.setRecords(collect);
        return goodsPage;
    }
}



