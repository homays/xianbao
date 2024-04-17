package com.example.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectVO {

    private Integer goodsId;
    private String goodsName;
    private String goodsImg;

}
