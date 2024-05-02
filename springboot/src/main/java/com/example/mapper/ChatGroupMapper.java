package com.example.mapper;

import com.example.entity.ChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatGroupMapper extends BaseMapper<ChatGroup> {

    @Select("select * from chat_group where user_id = #{userId}")
    List<ChatGroup> selectByUserId(Integer userId);

    @Select("select * from chat_group where chat_user_id = #{chatUserId} and user_id = #{userId}")
    ChatGroup selectByChatUserIdAndUserId(@Param("chatUserId") Integer chatUserId, @Param("userId") Integer userId);

}