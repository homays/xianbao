package com.example.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 *
 * @author Arrebol
 * @date 2024/4/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCollectVO {

    private Integer goodsId;
    private String goodsName;
    private String goodsImg;

}
