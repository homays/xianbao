package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 求助信息
 * @TableName help
 */
@TableName(value ="help")
@Data
public class Help implements Serializable {
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
     * 状态
     */
    private String status;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 发布时间
     */
    private String time;

    /**
     * 是否解决
     */
    private String solved;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}