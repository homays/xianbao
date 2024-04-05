package com.example.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * Description
 *
 * @author Arrebol
 * @date 2024/3/14
 */
@Data
public class RegisterDTO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色标识
     */
    private String role;

}
