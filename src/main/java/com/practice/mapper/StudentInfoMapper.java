package com.practice.mapper;

import com.practice.common.vo.StudentVo;
import com.practice.entity.StudentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 学生表 Mapper 接口
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Repository
public interface StudentInfoMapper extends BaseMapper<StudentInfo> {
    List<StudentInfo> getStudentList(StudentVo vo);
    Integer getStudentListCount(StudentVo vo);
}
