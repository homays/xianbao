package com.example.vo.req;

import lombok.Data;

/**
 * 前端请求posts数据DTO
 */
@Data
public class PostsQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String circle;
}
