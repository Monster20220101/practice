package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.TaskVo;
import com.practice.entity.TaskInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务管理 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface TaskInfoService extends IService<TaskInfo> {

    /**
     * 发布任务
     * @param vo
     * @return
     */
    Result addTask(TaskVo vo);

    /**
     * 修改任务
     * @param vo
     * @return
     */
    Result modifyTask(TaskVo vo);

    /**
     * 删除任务
     * @param vo
     * @return
     */
    Result deletedTask(TaskVo vo);

    /**
     * 获取任务管理列表
     * @param vo
     * @return
     */
    Result getTaskManagerList(TaskVo vo);

    /**
     * 获取单个任务的详情
     * @param vo
     * @return
     */
    Result getTaskById(TaskVo vo);

    /**
     * 获取学生列表带分数
     * @param vo
     * @return
     */
    Result getStudentScoreList(TaskVo vo);

    /**
     * 学生评分-自动判断是新增还是更新
     * @param vo
     * @return
     */
    Result score(TaskVo vo);

    /**
     * 获取任务列表带分数
     * @param vo
     * @return
     */
    Result getTaskListForStudent(TaskVo vo);

    /**
     * 定时任务执行方法
     */
    void updateTaskInfoTimeTask();
}
