package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.ChatGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ChatGroupService extends IService<ChatGroup> {

    void add(ChatGroup chatGroup);

    void deleteById(Integer id);

    void deleteBatch(List<Integer> ids);

    ChatGroup selectById(Integer id);

    List<ChatGroup> selectAll(ChatGroup chatGroup);

    Page<ChatGroup> selectPage(ChatGroup chatGroup, Integer pageNum, Integer pageSize);

    List<ChatGroup> selectUserGroup();
}
