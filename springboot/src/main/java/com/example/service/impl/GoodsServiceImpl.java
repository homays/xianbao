package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Goods;
import com.example.entity.User;
import com.example.mapper.GoodsMapper;
import com.example.mapper.UserMapper;
import com.example.service.GoodsService;
import com.example.utils.RedisUtil;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.GoodsQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.common.Constants.*;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void add(Goods goods) {
        String userId = ThreadLocalUtil.getUserId();
        User user = userMapper.selectById(userId);
        goods.setUserId(Integer.parseInt(userId));
        goods.setUserName(user.getUsername());
        goods.setDate(DateUtil.now());
        goods.setStatus(StatusEnum.TO_BE_REVIEWED.value);
        goodsMapper.insert(goods);
    }

    @Override
    public void updateByGoods(Goods goods) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String role = claims.get("role").toString();
        if (RoleEnum.USER.name().equals(role)) {
            goods.setStatus(StatusEnum.TO_BE_REVIEWED.value);
        }
        goodsMapper.updateById(goods);
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
        // 登录用户id
        String userId = ThreadLocalUtil.getUserId();
        // 判断用户是否点赞
        boolean isLike = redisUtil.isMember(XIANBAO_GOODS_LIKE + id, userId);
        goods.setUserLike(isLike);
        boolean isCollect = redisUtil.isMember(XIANBAO_GOODS_COLLECT + id, userId);
        goods.setUserCollect(isCollect);
        // 获取点赞数量
        Long likeCount = redisUtil.size(XIANBAO_GOODS_LIKE + id);
        Long collectCount = redisUtil.size(XIANBAO_GOODS_COLLECT + id);
        goods.setLikeCount(likeCount);
        goods.setCollectCount(collectCount);
        // 获取阅读数量
        Long readCount = redisUtil.incr(XIANBAO_GOODS_READCOUNT + id);
        goods.setReadCount(readCount.intValue());
        return goods;
    }

    @Override
    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    @Override
    public Page<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userId = Integer.parseInt(claims.get("userId").toString());
        String role = claims.get("role").toString();
        if (RoleEnum.USER.name().equals(role)) {
            goods.setUserId(userId);
        }
        Page<Goods> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(goods.getUserId() != null, Goods::getUserId, goods.getUserId());
        wrapper.like(StrUtil.isNotBlank(goods.getName()), Goods::getName, goods.getName());
        wrapper.like(StrUtil.isNotBlank(goods.getContent()), Goods::getContent, goods.getContent());
        wrapper.like(StrUtil.isNotBlank(goods.getAddress()), Goods::getAddress, goods.getAddress());
        wrapper.like(StrUtil.isNotBlank(goods.getCategory()), Goods::getContent, goods.getCategory());
        wrapper.orderByDesc(Goods::getDate);
        Page<Goods> goodsPage = goodsMapper.selectPage(page, wrapper);
        List<Goods> records = goodsPage.getRecords();
        List<Goods> collect = records.stream().peek(item -> {
            User user = userMapper.selectById(item.getUserId());
            item.setUserName(user.getName());
        }).collect(Collectors.toList());
        goodsPage.setRecords(collect);
        return goodsPage;
    }


    @Override
    public Page<Goods> selectFrontPage(GoodsQueryDTO goodsQueryDTO, Goods goods) {
        String sort = goodsQueryDTO.getSort();
        Page<Goods> page = new Page<>(goodsQueryDTO.getPageNum(), goodsQueryDTO.getPageSize());
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getStatus, StatusEnum.APPROVE.value);
        wrapper.eq(Goods::getSaleStatus, "上架");
        wrapper.like(StrUtil.isNotBlank(goodsQueryDTO.getCategory()), Goods::getCategory, goodsQueryDTO.getCategory());
        wrapper.like(StrUtil.isNotBlank(goodsQueryDTO.getName()), Goods::getName, goodsQueryDTO.getName());
        if (StrUtil.isNotBlank(sort) && sort.equals("最新")) {
            wrapper.orderByDesc(Goods::getId);
        }
        if (StrUtil.isNotBlank(sort) && sort.equals("最热")) {
            wrapper.orderByDesc(Goods::getReadCount);
        }
        Page<Goods> goodsPage = goodsMapper.selectPage(page, wrapper);
        List<Goods> records = goodsPage.getRecords();
        List<Goods> collect = records.stream().map(item -> {
            Goods dbGoods = this.selectById(item.getId());
            item.setUserName(dbGoods.getName());
            item.setUserLike(dbGoods.getUserLike());
            item.setUserCollect(dbGoods.getUserCollect());
            item.setLikeCount(dbGoods.getLikeCount());
            item.setCollectCount(dbGoods.getCollectCount());
            item.setReadCount(dbGoods.getReadCount());
            return dbGoods;
        }).collect(Collectors.toList());
        goodsPage.setRecords(collect);
        return goodsPage;
    }

    @Override
    public void like(Integer goodsId) {
        String userId = ThreadLocalUtil.getUserId();
        // 判断用户是否点赞
        boolean isLike = redisUtil.isMember(XIANBAO_GOODS_LIKE + goodsId, userId);
        if (!isLike) {
            // 存储商品中的点赞信息
            redisUtil.sAdd(XIANBAO_GOODS_LIKE + goodsId, userId);
            // 存储用户的点赞商品
            redisUtil.sAdd(XIANBAO_USER_GOODS_LIKE + userId, goodsId.toString());
        } else {
            // 删除点赞
            redisUtil.srem(XIANBAO_GOODS_LIKE + goodsId, userId);
            // 删除用户的点赞商品
            redisUtil.srem(XIANBAO_USER_GOODS_LIKE + userId, goodsId.toString());
        }
    }

    @Override
    public void collect(Integer goodsId) {
        // 登录用户id
        String userId = ThreadLocalUtil.getUserId();
        // 判断用户是否收藏
        boolean isCollect = redisUtil.isMember(XIANBAO_GOODS_COLLECT + goodsId, userId);
        if (!isCollect) {
            // 存储收藏
            redisUtil.sAdd(XIANBAO_GOODS_COLLECT + goodsId, userId);
            // 存储用户的收藏商品
            redisUtil.sAdd(XIANBAO_USER_GOODS_COLLECT + userId, goodsId.toString());
        } else {
            // 删除收藏
            redisUtil.srem(XIANBAO_GOODS_COLLECT + goodsId, userId);
            // 删除用户的收藏商品
            redisUtil.srem(XIANBAO_USER_GOODS_COLLECT + userId, goodsId.toString());
        }
    }

}




