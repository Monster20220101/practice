package com.practice.common.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 注册前端接收类
 */
@Data
public class TeacherVo extends BaseVo implements Serializable {
    @NotBlank(message = "工号不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "请输入正确的工号")
    private String teacherNum;
    @NotBlank(message = "姓名不能为空")
    private String teacherName;

    private Integer gender;
    private String birthday;
    private String workInfo;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入正确的手机号")
    private String telephone;
}
