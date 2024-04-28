package com.example.vo.req;

import lombok.Data;

/**
 * 前端请求orders数据DTO
 */
@Data
public class OrdersQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String orderNo;
    private String goodsName;
    private String orderStatus;
    /**
     * 买家：buyer
     * 卖家：seller
     */
    private String role;
}
