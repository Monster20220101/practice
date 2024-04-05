package com.practice.common.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户前端接收类
 */
@Data
public class UserVo extends BaseVo implements Serializable {
    /**
     * 用户名（账号）
     */
    @NotBlank(message = "用户名不能为空")
    private String accountNum;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    private String newPassword;

    private String telephone;
}
