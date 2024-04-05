package com.practice.service;

import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.DailyReportVo;
import com.practice.entity.DailyReport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 日报表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface DailyReportService extends IService<DailyReport> {

    Result addDailyReport(DailyReportVo vo);

    Result addCommentDailyReport(UserDto userDto, DailyReportVo vo);

    Result modifyDailyReport(UserDto userDto, DailyReportVo vo);

    Result deletedDailyReport(DailyReportVo vo);

    Result getDailyReportList(DailyReportVo vo);
}
