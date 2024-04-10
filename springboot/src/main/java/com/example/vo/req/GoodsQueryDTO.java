package com.example.vo.req;

import lombok.Data;

/**
 * 前端请求goods数据DTO
 */
@Data
public class GoodsQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String category;
    private String sort;
}
