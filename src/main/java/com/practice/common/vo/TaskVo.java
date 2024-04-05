package com.practice.common.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class TaskVo extends BaseVo implements Serializable {

    private Long id;


    /**
     * 任务名称
     */
    @NotBlank(message = "缺少任务名称")
    private String taskName;

    /**
     * 任务内容
     */
    @NotBlank(message = "缺少任务内容")
    private String content;

    /**
     * 开始时间
     */
    @NotNull(message = "缺少开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "缺少结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 传给前端的格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") // 接收前端的格式
    private LocalDateTime endTime;


    /**
     * 任务状态 0-未开始 1-进行中 2-已结束
     */
    private Integer taskStatus;

    /**
     * 学生id
     */
    private Long StudentId;

    /**
     * 分数
     */
    private Integer score;
}
