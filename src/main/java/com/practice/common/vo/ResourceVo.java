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
public class ResourceVo extends BaseVo implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 资源名称
     */
    @NotBlank(message = "缺少资源名称")
    private String resourceName;

    /**
     * 资源链接
     */
    private String resourceUrl;

    /**
     * 资源类型 1-链接 2-文件
     */
    private String resourceType;

    /**
     * 关联教师id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long relationTeacherId;
}
