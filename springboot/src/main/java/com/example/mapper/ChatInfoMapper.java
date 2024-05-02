package com.example.mapper;

import com.example.entity.ChatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatInfoMapper extends BaseMapper<ChatInfo> {

    @Select("select count(*) from chat_info where user_id = #{chatUserId} and chat_user_id = #{userId} and isread = '否'")
    Integer selectUnReadChatNum(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

    List<ChatInfo> selectUserChat(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

    @Update("update chat_info set isread = '是' where user_id = #{chatUserId} and chat_user_id = #{userId}")
    void updateRead(@Param("userId") Integer userId, @Param("chatUserId") Integer chatUserId);

}




