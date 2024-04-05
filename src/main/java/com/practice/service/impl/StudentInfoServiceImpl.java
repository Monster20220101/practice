package com.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.StudentVo;
import com.practice.entity.StudentInfo;
import com.practice.entity.UserInfo;
import com.practice.mapper.StudentInfoMapper;
import com.practice.mapper.UserInfoMapper;
import com.practice.service.StudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements StudentInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Override
    public Result addStudent(StudentVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        // 验证账号是否存在
        Integer count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>().eq("account_num", vo.getStudentNum()));
        if(count > 0) {
            return Result.error("该账号已存在");
        }
        // 插入信息
        // 用户信息--user 教师信息-teacher
        UserInfo userInfo = new UserInfo();
        userInfo.setAccountNum(vo.getStudentNum());
        // 设置默认密码 -- 使用学号后六位，不足6位的学号为本身
        String password = vo.getStudentNum();
        if(password.length() > 6){
            password = password.substring(password.length() - 6);
        }
        userInfo.setPassword(SecureUtil.md5(password));
        userInfo.setUserType(2);
        userInfo.setCreatedBy(userDto.getId());

        // 插入数据库
        boolean save = userInfoMapper.insert(userInfo) > 0;
        if(!save){
            return Result.error("用户保存异常");
        }
        // 添加学生信息
        StudentInfo studentInfo = new StudentInfo();
        BeanUtil.copyProperties(vo, studentInfo);
        // 关联新建的用户id
        studentInfo.setRelationUserId(userInfo.getId());
        // 确保学生关联班级id
        studentInfo.setRelationClassId(vo.getRelationClassId());
        // 设置创建者
        studentInfo.setCreatedBy(userDto.getId());

        // 保存
        boolean b = super.save(studentInfo);
        return b ? Result.success("学生添加成功") : Result.error("学生保存异常");
    }

    @Override
    public Result modifyStudent(StudentVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        StudentInfo studentInfo = super.getById(vo.getId());

        if(!studentInfo.getStudentNum().equals(vo.getStudentNum())){
            // 验证账号是否存在
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("account_num", vo.getStudentNum());
            int count = userInfoMapper.selectCount(wrapper);
            if(count > 0){
                return Result.error("该账号已存在");
            }
            UserInfo userInfo = userInfoMapper.selectById(studentInfo.getRelationUserId());
            userInfo.setAccountNum(vo.getStudentNum());
            userInfoMapper.updateById(userInfo);
        }
        // 拷贝其它信息
        BeanUtil.copyProperties(vo, studentInfo);
        // 设置创建者
        studentInfo.setUpdatedBy(userDto.getId());
        boolean b = super.updateById(studentInfo);

        return b ? Result.success("更新成功") : Result.error("更新失败");
    }

    @Override
    public Result deletedStudent(StudentVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        StudentInfo studentInfo = super.getById(vo.getId());
        if(studentInfo == null || studentInfo.getIsDeleted()==BaseVo.DELETED){
            return Result.error("请勿重复删除");
        }
        studentInfo.setIsDeleted(BaseVo.DELETED);
        studentInfo.setUpdatedBy(userDto.getId());
        boolean b = super.updateById(studentInfo);
        if(!b) {
            return Result.error("删除失败");
        }

        UserInfo userInfo = userInfoMapper.selectById(studentInfo.getRelationUserId());

        userInfo.setIsDeleted(BaseVo.DELETED);
        userInfo.setUpdatedBy(userDto.getId());
        userInfoMapper.updateById(userInfo);
        return Result.success("删除成功");
    }

    @Override
    public Result getStudentList(StudentVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        // 放入teacherId查询
        vo.setTeacherId(userDto.getTeacherInfo().getId());
        // 查询
        List<StudentInfo> list = studentInfoMapper.getStudentList(vo);
        Integer total = studentInfoMapper.getStudentListCount(vo);

        return Result.success("请求成功", MapUtil.builder()
                .put("list", list)
                .put("total", total)
                .put("pages", BaseVo.calculatePages(vo.getSize(), total))
                .map());
    }
}
