package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.Help;
import com.example.service.HelpService;
import com.example.vo.req.HelpDTO;
import com.example.vo.req.HelpQueryDTO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 求助信息前端操作接口
 **/
@RestController
@RequestMapping("/help")
public class HelpController {

    @Resource
    private HelpService helpService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody HelpDTO helpDTO) {
        helpService.add(helpDTO);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        helpService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        helpService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Help help) {
        helpService.updateHelp(help);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Help help = helpService.selectById(id);
        return Result.success(help);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Help help) {
        List<Help> list = helpService.selectAll(help);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Help help,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Help> page = helpService.selectPage(help, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询
     */
    @PostMapping("/selectFrontPage")
    public Result selectFrontPage(Help help, @RequestBody HelpQueryDTO helpQueryDTO) {
        Page<Help> page = helpService.selectFrontPage(help, helpQueryDTO);
        return Result.success(page);
    }

}