package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.ChatInfo;
import com.example.service.ChatInfoService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 聊天信息前端操作接口
 **/
@RestController
@RequestMapping("/chatInfo")
public class ChatInfoController {

    @Resource
    private ChatInfoService chatInfoService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody ChatInfo chatInfo) {
        chatInfoService.add(chatInfo);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        chatInfoService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        chatInfoService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody ChatInfo chatInfo) {
        chatInfoService.updateById(chatInfo);
        return Result.success();
    }

    @PutMapping("/updateRead/{chatUserId}")
    public Result updateRead(@PathVariable Integer chatUserId) {
        chatInfoService.updateRead(chatUserId);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        ChatInfo chatInfo = chatInfoService.selectById(id);
        return Result.success(chatInfo);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(ChatInfo chatInfo) {
        List<ChatInfo> list = chatInfoService.selectAll(chatInfo);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(ChatInfo chatInfo,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ChatInfo> page = chatInfoService.selectPage(chatInfo, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 查询聊天记录
     */
    @GetMapping("/selectUserChat/{chatUserId}")
    public Result selectUserChat(@PathVariable Integer chatUserId) {
        List<ChatInfo> chatInfoList = chatInfoService.selectUserChat(chatUserId);
        return Result.success(chatInfoList);
    }

}