package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.ChatGroup;
import com.example.service.ChatGroupService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天组前端操作接口
 **/
@RestController
@RequestMapping("/chatGroup")
public class ChatGroupController {

    @Resource
    private ChatGroupService chatGroupService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody ChatGroup chatGroup) {
        chatGroupService.add(chatGroup);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        chatGroupService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        chatGroupService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody ChatGroup chatGroup) {
        chatGroupService.updateById(chatGroup);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        ChatGroup chatGroup = chatGroupService.selectById(id);
        return Result.success(chatGroup);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(ChatGroup chatGroup) {
        List<ChatGroup> list = chatGroupService.selectAll(chatGroup);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ChatGroup chatGroup,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ChatGroup> page = chatGroupService.selectPage(chatGroup, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 聊天列表
     */
    @GetMapping("/selectUserGroup")
    public Result selectUserGroup() {
        List<ChatGroup> chatGroupList = chatGroupService.selectUserGroup();
        return Result.success(chatGroupList);
    }

}