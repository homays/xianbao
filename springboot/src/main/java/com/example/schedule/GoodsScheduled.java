package com.example.schedule;

import cn.hutool.core.util.StrUtil;
import com.example.entity.Goods;
import com.example.service.GoodsService;
import com.example.utils.RedisUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
import java.util.Set;

import static com.example.common.Constants.XIANBAO_GOODS_READCOUNT;

/**
 * 商品数据定时任务
 *
 * @author Arrebol
 * @date 2024/4/25
 */
@Component
public class GoodsScheduled {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private GoodsService goodsService;
    
    @Scheduled(cron = "* 0/30 * * * ? ")
    public void updateReadCount() {
        // 从redis 获取数据
        List<Goods> list = goodsService.list();
        list.stream().map(Goods::getId).forEach(id -> {
            String key = XIANBAO_GOODS_READCOUNT + id;
            String value = redisUtil.get(key);
            if (StrUtil.isNotBlank(value)) {
                goodsService.update().setSql("read_count = " + value).eq("id", id).update();
            }
        });
    }

}
