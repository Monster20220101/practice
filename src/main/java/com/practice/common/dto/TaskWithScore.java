package com.practice.common.dto;

import com.practice.entity.TaskInfo;
import lombok.Data;

@Data
public class TaskWithScore extends TaskInfo {
    /**
     * 学生分数
     */
    private Integer score;
}
