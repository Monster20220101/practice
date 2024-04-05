package com.practice.common.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class StudentVo extends BaseVo implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 学生姓名
     */
    @NotBlank(message = "缺少学生姓名")
    private String studentName;

    /**
     * 学号（账号）
     */
    @NotBlank(message = "缺少学号")
    private String studentNum;
}
