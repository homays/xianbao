package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.ChatInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ChatInfoService extends IService<ChatInfo> {

    Integer selectUnReadChatNum(Integer userId, Integer chatUserId);

    void add(ChatInfo chatInfo);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    void updateRead(Integer chatUserId);

    ChatInfo selectById(Integer id);

    List<ChatInfo> selectAll(ChatInfo chatInfo);

    Page<ChatInfo> selectPage(ChatInfo chatInfo, Integer pageNum, Integer pageSize);

    List<ChatInfo> selectUserChat(Integer chatUserId);
}
