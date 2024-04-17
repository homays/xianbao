package com.example.controller;

import com.example.common.Result;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.RedisUtil;
import com.example.utils.ThreadLocalUtil;
import com.example.vo.req.UserCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.common.Constants.XIANBAO_GOODS_COLLECT;
import static com.example.common.Constants.XIANBAO_USER_GOODS_COLLECT;

/**
 * Description
 * 收藏
 * @Author Arrebol
 * @Date 2024/4/17
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询所有收藏
     */
    @GetMapping("/selectAll")
    public Result selectCollect() {
        String userId = ThreadLocalUtil.getUserId();
        Set<String> goodIds = redisUtil.sMembers(XIANBAO_USER_GOODS_COLLECT + userId);
        List<Integer> goodIdsList = goodIds.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<UserCollectVO> collect = goodIdsList.stream().map(item -> {
            Goods goods = goodsMapper.selectById(item);
            return UserCollectVO.builder()
                    .goodsId(goods.getId())
                    .goodsName(goods.getName())
                    .goodsImg(goods.getImg())
                    .build();
        }).collect(Collectors.toList());
        return Result.success(collect);
    }

    /**
     * 删除收藏
     * @param goodsId 商品id
     */
    @DeleteMapping("/delete/{goodsId}")
    public Result deleteCollect(@PathVariable Integer goodsId) {
        String userId = ThreadLocalUtil.getUserId();
        // 删除商品中的收藏数据
        redisUtil.srem(XIANBAO_GOODS_COLLECT + goodsId, userId);
        // 删除用户中的收藏数据
        redisUtil.srem(XIANBAO_USER_GOODS_COLLECT + userId, goodsId.toString());
        return Result.success();
    }

}
