package com.example.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.ChatGroup;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.ChatGroupService;
import com.example.mapper.ChatGroupMapper;
import com.example.service.ChatInfoService;
import com.example.utils.ThreadLocalUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatGroupServiceImpl extends ServiceImpl<ChatGroupMapper, ChatGroup> implements ChatGroupService{

    @Resource
    private ChatGroupMapper chatGroupMapper;
    @Resource
    private ChatInfoService chatInfoService;
    @Resource
    private UserMapper userMapper;
    /**
     * 两个用户都要插入
     */
    @Override
    public void add(ChatGroup chatGroup) {
        ChatGroup dbChatGroup = chatGroupMapper.selectByChatUserIdAndUserId(chatGroup.getChatUserId(), chatGroup.getUserId());
        if (dbChatGroup == null) {
            chatGroupMapper.insert(chatGroup);
        }
        ChatGroup dbChatGroup1 = chatGroupMapper.selectByChatUserIdAndUserId(chatGroup.getUserId(), chatGroup.getChatUserId());
        if (dbChatGroup1 == null) {
            dbChatGroup1 = new ChatGroup();
            dbChatGroup1.setChatUserId(chatGroup.getUserId());
            dbChatGroup1.setUserId(chatGroup.getChatUserId());
            chatGroupMapper.insert(dbChatGroup1);
        }
    }

    @Override
    public void deleteById(Integer id) {
        chatGroupMapper.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        chatGroupMapper.deleteBatchIds(ids);
    }

    @Override
    public ChatGroup selectById(Integer id) {
        return chatGroupMapper.selectById(id);
    }

    @Override
    public List<ChatGroup> selectAll(ChatGroup chatGroup) {
        return chatGroupMapper.selectList(null);
    }

    @Override
    public Page<ChatGroup> selectPage(ChatGroup chatGroup, Integer pageNum, Integer pageSize) {
        Page<ChatGroup> page = new Page<>(pageNum, pageSize);
        return chatGroupMapper.selectPage(page, null);
    }

    @Override
    public List<ChatGroup> selectUserGroup() {
        List<ChatGroup> chatGroupList = new ArrayList<>();
        Integer userId = Integer.parseInt(ThreadLocalUtil.getUserId());

        List<ChatGroup> userChatGroupList = chatGroupMapper.selectByUserId(userId);
        for (ChatGroup chatGroup : userChatGroupList) {
            Integer chatUserId = chatGroup.getChatUserId();
            Integer num = chatInfoService.selectUnReadChatNum(userId, chatUserId);
            chatGroup.setChatNum(num);
            User chatUser = userMapper.selectById(chatUserId);
            chatGroup.setChatUserName(chatUser.getName());
            chatGroup.setChatUserAvatar(chatUser.getAvatar());
            chatGroupList.add(chatGroup);
        }
        return chatGroupList;
    }

}




