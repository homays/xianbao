package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 订单信息
 * @TableName orders
 */
@TableName(value ="orders")
@Data
public class Orders implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 总价
     */
    private BigDecimal total;

    /**
     * 下单时间
     */
    private String time;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 下单人ID
     */
    private Integer userId;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 收货人名称
     */
    private String userName;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 卖家ID
     */
    private Integer saleId;

    /**
     * 卖家名称
     */
    @TableField(exist = false)
    private String saleName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}