package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.ChatInfo;
import com.example.service.ChatInfoService;
import com.example.mapper.ChatInfoMapper;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class ChatInfoServiceImpl extends ServiceImpl<ChatInfoMapper, ChatInfo> implements ChatInfoService{

    @Resource
    private ChatInfoMapper chatInfoMapper;

    @Override
    public void add(ChatInfo chatInfo) {
        chatInfo.setTime(DateUtil.now());
        chatInfoMapper.insert(chatInfo);
    }

    @Override
    public void deleteById(Integer id) {
        chatInfoMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        chatInfoMapper.deleteBatchIds(ids);
    }

    @Override
    public ChatInfo selectById(Integer id) {
        return chatInfoMapper.selectById(id);
    }

    @Override
    public List<ChatInfo> selectAll(ChatInfo chatInfo) {
        return chatInfoMapper.selectList(null);
    }

    @Override
    public Page<ChatInfo> selectPage(ChatInfo chatInfo, Integer pageNum, Integer pageSize) {
        Page<ChatInfo> page = new Page<>(pageNum, pageSize);
        return chatInfoMapper.selectPage(page, null);
    }

    @Override
    public List<ChatInfo> selectUserChat(Integer chatUserId) {
        Integer userId = Integer.parseInt(ThreadLocalUtil.getUserId());
        return chatInfoMapper.selectUserChat(userId, chatUserId);
    }

    @Override
    public Integer selectUnReadChatNum(Integer userId, Integer chatUserId) {
        return chatInfoMapper.selectUnReadChatNum(userId, chatUserId);
    }

    @Override
    public void updateRead(Integer chatUserId) {
        Integer userId = Integer.parseInt(ThreadLocalUtil.getUserId());
        chatInfoMapper.updateRead(userId, chatUserId);
    }
}




