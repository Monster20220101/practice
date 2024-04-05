package com.practice.common.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.practice.entity.ClassInfo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class DailyReportVo extends BaseVo implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 标题
     */
    @NotBlank(message = "缺少日报标题")
    private String title;

    /**
     * 内容
     */
    @NotBlank(message = "缺少日报内容")
    private String content;

    /**
     * 教师评价
     */
    private String comment;

    private Long userId;

    private List<Long> classList;
}
