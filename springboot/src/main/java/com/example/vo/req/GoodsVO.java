package com.example.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVO {

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
     * 所属用户
     */
    private String userName;

    /**
     * 上架状态
     */
    private String saleStatus;

    /**
     * 浏览量
     */
    private Integer readCount;

}
