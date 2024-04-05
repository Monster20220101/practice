package com.practice.common.vo;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
public class DiscussVo extends BaseVo implements Serializable {

    /**
     *  答疑id
     */
    private Long id;

    /**
     * 答疑内容
     */
    @NotBlank(message = "缺少内容")
    private String content;

    /**
     * 关联讨论id
     */
    private Long relationDiscussId;
}
