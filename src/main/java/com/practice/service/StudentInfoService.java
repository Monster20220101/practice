package com.practice.service;

import com.practice.common.lang.Result;
import com.practice.common.vo.StudentVo;
import com.practice.entity.StudentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 学生表 服务类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
public interface StudentInfoService extends IService<StudentInfo> {

    /**
     * 添加学生
     * @param vo
     * @return
     */
    Result addStudent(StudentVo vo);

    /**
     * 更新学生
     * @param vo
     * @return
     */
    Result modifyStudent(StudentVo vo);

    /**
     * 删除学生
     * @param vo
     * @return
     */
    Result deletedStudent(StudentVo vo);

    /**
     * 获取学生列表
     * @param vo
     * @return
     */
    Result getStudentList(StudentVo vo);
}
