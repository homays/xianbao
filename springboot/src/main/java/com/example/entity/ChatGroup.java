package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@TableName(value ="chat_group")
@Data
public class ChatGroup implements Serializable {
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

    @TableField(exist = false)
    private String chatUserName;

    @TableField(exist = false)
    private String chatUserAvatar;

    @TableField(exist = false)
    private Integer chatNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}