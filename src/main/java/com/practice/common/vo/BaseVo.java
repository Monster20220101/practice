package com.practice.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseVo implements Serializable {
    public static final Integer NOT_DELETED = 0;
    public static final Integer DELETED = 1;

    // 页数
    private Integer page;
    // 数量
    private Integer size;
    // 查询关键字
    private String key;

    // 偏移量
    private Integer offset;

    /**
     * 关联班级id
     */
    private Long relationClassId;

    /**
     * 教师id
     */
    private Long teacherId;

    // 获取偏移量
    public Integer getOffset(){
        return (this.page - 1) * this.size;
    }

    public static Integer calculatePages(Integer size, Integer total) {
        return (total+size-1)/size;
    }
}
