package com.practice.common.dto;

import com.practice.entity.DailyReport;
import com.practice.entity.TaskInfo;
import lombok.Data;

@Data
public class DailyDto extends DailyReport {
    String studentName;
}
