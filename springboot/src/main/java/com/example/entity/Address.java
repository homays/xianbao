package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 收货地址
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 联系人
     */
    private String name;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 关联用户
     */
    private Integer userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}