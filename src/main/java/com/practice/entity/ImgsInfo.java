package com.practice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 第五组
 * @since 2022-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImgsInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片自己的id 不重复
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "img_id", type = IdType.ASSIGN_ID)
    private Long imgId;

    /**
     * 图片所属图片集id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long imgsId;

    /**
     * 图片路径
     */
    private String imgUrl;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createBy;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateBy;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Integer isDelete;


}
