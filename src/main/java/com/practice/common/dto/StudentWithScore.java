package com.practice.common.dto;

import com.practice.entity.StudentInfo;
import lombok.Data;

@Data
public class StudentWithScore extends StudentInfo {
    /**
     * 学生分数
     */
    private Integer score;
}
