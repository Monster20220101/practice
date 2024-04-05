package com.practice.common.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class ClassVo extends BaseVo implements Serializable {

    /**
     * 班级id
     */
    private Long id;


    /**
     * 班级名称
     */
    @NotBlank(message = "缺少班级名称")
    private String className;

    /**
     * 实训名称
     */
    @NotBlank(message = "缺少实训名称")
    private String practiceTitle;

    /**
     * 实训内容
     */
    @NotBlank(message = "缺少实训内容")
    private String practiceContent;
}
