package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 聊天信息
 * @TableName chat_info
 */
@TableName(value ="chat_info")
@Data
public class ChatInfo implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 聊天用户ID
     */
    private Integer chatUserId;

    /**
     * 当前用户ID
     */
    private Integer userId;

    /**
     * 聊天内容
     */
    private String text;

    /**
     * 是否已读
     */
    private String isread;

    /**
     * 时间
     */
    private String time;

    @TableField(exist = false)
    private String chatUserName;

    @TableField(exist = false)
    private String chatUserAvatar;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String userAvatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}