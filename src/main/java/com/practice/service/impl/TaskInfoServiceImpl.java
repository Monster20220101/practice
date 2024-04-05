package com.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.dto.TaskWithScore;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.TaskVo;
import com.practice.common.dto.StudentWithScore;
import com.practice.entity.StudentInfo;
import com.practice.entity.TaskGrade;
import com.practice.entity.TaskInfo;
import com.practice.mapper.TaskInfoMapper;
import com.practice.service.StudentInfoService;
import com.practice.service.TaskGradeService;
import com.practice.service.TaskInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 任务管理 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Slf4j
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements TaskInfoService {

    @Autowired
    private TaskGradeService taskGradeService;
    @Autowired
    private StudentInfoService studentInfoService;

    @Override
    public Result addTask(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        TaskInfo taskInfo = new TaskInfo();
        BeanUtil.copyProperties(vo, taskInfo);
        taskInfo.setRelationTeacherId(userDto.getTeacherInfo().getId());
        taskInfo.setCreatedBy(userDto.getId());

        if(vo.getStartTime().compareTo(vo.getEndTime()) >= 0){
            return Result.error("开始时间不能晚于结束时间");
        }

        // 手动插入id，不然等会找不到
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);//自动生成id 雪花算法（随机生成一串数字）
        long id = snowflake.nextId();
        taskInfo.setId(id);
        boolean save = super.save(taskInfo);
        if(!save){
            return Result.error("添加失败");
        }
        super.baseMapper.updateTaskStatusAuto();
        /**
         * 给该关联班级的所有学生添加一个评分记录
         * 评分无需新增记录，优先查询表中数据，如果查到再添加
         * -- 保护数据唯一性
         */
        QueryWrapper<StudentInfo> wrapper = new QueryWrapper<StudentInfo>()
                .eq("relation_class_id", vo.getRelationClassId());
        List<StudentInfo> list = studentInfoService.list(wrapper);
        for(StudentInfo studentInfo : list){
            TaskGrade taskGrade = new TaskGrade();
            taskGrade.setCreatedBy(userDto.getId());
            taskGrade.setScore(0);
            taskGrade.setRelationTaskId(id);
            taskGrade.setRelationStudentId(studentInfo.getId());
            taskGrade.setRelationTeacherId(userDto.getId());
            save = taskGradeService.save(taskGrade);
            if(!save){
                return Result.error("批量初始化学生成绩失败");
            }
        }

        return Result.success("添加成功");
    }

    @Override
    public Result modifyTask(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        TaskInfo taskInfo = super.getById(vo.getId());
        if(taskInfo==null||taskInfo.getIsDeleted().equals(BaseVo.DELETED)){
            return Result.error("该任务不存在或者已删除");
        }

        BeanUtil.copyProperties(vo, taskInfo);
        taskInfo.setUpdatedBy(userDto.getId());

        if(vo.getStartTime().compareTo(vo.getEndTime())>0){
            return Result.error("开始时间不能晚于结束时间");
        }

        boolean b = super.updateById(taskInfo);
        if(!b){
            Result.error("编辑失败");
        }
        super.baseMapper.updateTaskStatusAuto();
        return Result.success("编辑成功");
    }

    @Override
    public Result deletedTask(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        TaskInfo taskInfo = super.getById(vo.getId());
        if(taskInfo==null||taskInfo.getIsDeleted().equals(BaseVo.DELETED)){
            return Result.error("该任务不存在或者已删除");
        }
        taskInfo.setIsDeleted(BaseVo.DELETED);
        taskInfo.setUpdatedBy(userDto.getId());

        boolean b = super.updateById(taskInfo);

        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getTaskManagerList(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        Page<TaskInfo> page = new Page<>(vo.getPage(), vo.getSize());
        QueryWrapper<TaskInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("relation_teacher_id", userDto.getTeacherInfo().getId())
                .eq("is_deleted", BaseVo.NOT_DELETED);
        if(vo.getRelationClassId()!=null){
            wrapper.eq("relation_class_id", vo.getRelationClassId());
        }
        if(!StringUtils.isBlank(vo.getKey())) {
            wrapper.like("task_name", vo.getKey());
        }
        Page<TaskInfo> iPage = super.page(page, wrapper);

        return Result.success("请求成功", MapUtil.builder()
            .put("list", iPage.getRecords())
            .put("total", iPage.getTotal())
            .put("pages", iPage.getPages())
            .map());
    }

    @Override
    public Result getTaskById(TaskVo vo) {

        TaskInfo taskInfo = super.getById(vo.getId());
        return taskInfo == null ? Result.error("获取详情失败") : Result.success("获取成功", taskInfo);
    }

    @Override
    public Result getStudentScoreList(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        // shiro-当前缓存中的userDto
        TaskInfo taskInfo = super.getById(vo.getId());
        if(!taskInfo.getRelationTeacherId().equals(userDto.getTeacherInfo().getId())){
            return Result.error("只能查看自己的任务");
        }
        vo.setRelationClassId(taskInfo.getRelationClassId());
        List<StudentWithScore> list = super.baseMapper.getStudentScoreList(vo);//每页内容
        Integer total = super.baseMapper.getStudentScoreListCount(vo);//页数
        // 平均分
        Double avgScore = super.baseMapper.getAvgScoreByTaskId(vo.getId());


        return Result.success("请求成功", MapUtil.builder()
                .put("list", list)
                .put("total", total)
                .put("pages", BaseVo.calculatePages(vo.getSize(),total))
                .put("avgScore", avgScore)
                .map());
    }

    @Override
    public Result score(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        QueryWrapper<TaskGrade> wrapper = new QueryWrapper<>();
        wrapper.eq("relation_student_id", vo.getStudentId())
                .eq("relation_task_id", vo.getId())
                .eq("is_deleted", BaseVo.NOT_DELETED);
        TaskGrade taskGrade = taskGradeService.getOne(wrapper);
        if(taskGrade==null){
            taskGrade = new TaskGrade();
            taskGrade.setRelationStudentId(vo.getStudentId());
            taskGrade.setRelationTaskId(vo.getId());
            taskGrade.setRelationTeacherId(userDto.getTeacherInfo().getId());
            taskGrade.setCreatedBy(userDto.getId());
        } else {
            taskGrade.setUpdatedBy(userDto.getId());
        }
        taskGrade.setScore(vo.getScore());
        // saveOrUpdate方法：通过id是否为空来判断是save还是update
        boolean b = taskGradeService.saveOrUpdate(taskGrade);
        return b ? Result.success("评分成功") : Result.error("评分失败");
    }

    @Override
    public Result getTaskListForStudent(TaskVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        vo.setRelationClassId(userDto.getStudentInfo().getRelationClassId());
        vo.setStudentId(userDto.getStudentInfo().getId());

        List<TaskWithScore> list = super.baseMapper.getTaskWithScoreList(vo);
        Integer total = super.baseMapper.getTaskWithListCount(vo);

        return Result.success("请求成功", MapUtil.builder()
                .put("list",list)
                .put("total",total)
                .put("pages",BaseVo.calculatePages(vo.getSize(), total))
                .map());
    }

    @Override
    public void updateTaskInfoTimeTask() {
        System.err.println("---------定时任务开始更新任务状态---------");
        super.baseMapper.updateTaskStatusAuto();
    }
}
