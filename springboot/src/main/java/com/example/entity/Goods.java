package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 二手商品
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 详情
     */
    private String content;

    /**
     * 发货地址
     */
    private String address;

    /**
     * 图片
     */
    private String img;

    /**
     * 上架日期
     */
    private String date;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 分类
     */
    private String category;

    /**
     * 所属用户ID
     */
    private Integer userId;

    /**
     * 上架状态
     */
    private String saleStatus;

    /**
     * 浏览量
     */
    private Integer readCount;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 是否点赞
     */
    @TableField(exist = false)
    private Boolean userLike;

    /**
     * 是否收藏
     */
    @TableField(exist = false)
    private Boolean userCollect;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}