package com.practice.mapper;

import com.practice.common.dto.TaskWithScore;
import com.practice.common.vo.TaskVo;
import com.practice.common.dto.StudentWithScore;
import com.practice.entity.TaskInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 任务管理 Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Repository
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {

    List<StudentWithScore> getStudentScoreList(TaskVo vo);

    Integer getStudentScoreListCount(TaskVo vo);

    Double getAvgScoreByTaskId(Long id);

    List<TaskWithScore> getTaskWithScoreList(TaskVo vo);

    Integer getTaskWithListCount(TaskVo vo);

    /**
     * 根据当前时间、start_time、end_time自动更新task_status
     */
    void updateTaskStatusAuto();
}
