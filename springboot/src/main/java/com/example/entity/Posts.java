package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 帖子
 * @TableName posts
 */
@TableName(value ="posts")
@Data
public class Posts implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String img;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private String time;

    /**
     * 圈子
     */
    private String circle;

    /**
     * 简介
     */
    private String descr;

    /**
     * 浏览量
     */
    private Integer readCount;

    /**
     * 审核状态
     */
    private String status;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}