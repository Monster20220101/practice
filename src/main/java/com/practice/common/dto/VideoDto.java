package com.practice.common.dto;

import com.practice.entity.DailyReport;
import com.practice.entity.VideoInfo;
import lombok.Data;

@Data
public class VideoDto extends VideoInfo {
    String studentName;
}
