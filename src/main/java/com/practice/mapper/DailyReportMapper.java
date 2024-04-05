package com.practice.mapper;

import com.practice.common.dto.DailyDto;
import com.practice.common.vo.DailyReportVo;
import com.practice.entity.DailyReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 日报表 Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Repository
public interface DailyReportMapper extends BaseMapper<DailyReport> {


    List<DailyDto> getDailyReportList(DailyReportVo vo);

    Integer getDailyReportListCount(DailyReportVo vo);
}
