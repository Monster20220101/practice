package com.practice.common.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class VideoVo extends BaseVo implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 视频标题
     */
    @NotBlank(message = "缺少视频标题")
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 视频链接
     */
    private String videoUrl;

    private Long userId;
}
