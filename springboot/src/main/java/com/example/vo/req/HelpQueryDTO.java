package com.example.vo.req;

import lombok.Data;

/**
 * 前端请求help数据DTO
 */
@Data
public class HelpQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String title;
}
