package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Circles;
import com.example.service.CirclesService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 圈子前端操作接口
 **/
@RestController
@RequestMapping("/circles")
public class CirclesController {

    @Resource
    private CirclesService circlesService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Circles circles) {
        circlesService.add(circles);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        circlesService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        circlesService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Circles circles) {
        circlesService.updateById(circles);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Circles circles = circlesService.selectById(id);
        return Result.success(circles);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Circles circles) {
        List<Circles> list = circlesService.selectAll(circles);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Circles circles,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Circles> page = circlesService.selectPage(circles, pageNum, pageSize);
        return Result.success(page);
    }

}