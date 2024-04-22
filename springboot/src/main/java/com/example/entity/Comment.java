package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 评论表
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人
     */
    private Integer userId;

    /**
     * 父级ID
     */
    private Integer pid;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 关联ID
     */
    private Integer fid;

    /**
     * 模块
     */
    private String module;

    /**
     * 根节点ID
     */
    private Integer rootId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}