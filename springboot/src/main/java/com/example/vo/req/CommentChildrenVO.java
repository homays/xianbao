package com.example.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentChildrenVO {

    private String content;
    private Integer userId;
    private Integer pid;
    private String time;
    private Integer fid;
    private String module;
    private Integer rootId;
    private String userName;
    private String userImg;
    private String parentName;
    private String parentImg;

}
