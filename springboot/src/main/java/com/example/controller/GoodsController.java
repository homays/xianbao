package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.common.enums.StatusEnum;
import com.example.entity.Goods;
import com.example.service.GoodsService;
import com.example.vo.req.GoodsQueryDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 二手商品前端操作接口
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Goods goods) {
        goodsService.add(goods);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        goodsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        goodsService.updateByGoods(goods);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Goods goods = goodsService.selectById(id);
        return Result.success(goods);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Goods goods) {
        List<Goods> list = goodsService.selectAll(goods);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Goods goods,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 前端首页分页查询
     */
    @PostMapping("/selectFrontPage")
    public Result selectFrontPage(@RequestBody GoodsQueryDTO goodsQueryDTO, Goods goods) {
        Page<Goods> page = goodsService.selectFrontPage(goodsQueryDTO, goods);
        return Result.success(page);
    }

    /**
     * 点赞
     */
    @PostMapping("/like/{id}")
    public Result like(@PathVariable("id") Integer goodsId) {
        goodsService.like(goodsId);
        return Result.success();
    }

    /**
     * 收藏
     */
    @PostMapping("/collect/{id}")
    public Result collect(@PathVariable("id") Integer goodsId) {
        goodsService.collect(goodsId);
        return Result.success();
    }

}