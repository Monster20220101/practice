package com.practice.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.ClassVo;
import com.practice.entity.ClassInfo;
import com.practice.mapper.ClassInfoMapper;
import com.practice.service.ClassInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements ClassInfoService {

    @Override
    public Result addClass(ClassVo vo) {
        // 获取当前操作用户
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        // 班级查重 String className = vo.getClassName() 待补充

        // 添加班级
        ClassInfo classInfo = new ClassInfo();
        BeanUtil.copyProperties(vo, classInfo);
        // 操作用户id
        classInfo.setCreatedBy(userDto.getId());
        // 关联教师id
        classInfo.setRelationTeacherId(userDto.getTeacherInfo().getId());
        boolean save = super.save(classInfo);
        return save ? Result.success("添加成功") : Result.error("添加失败");
    }

    @Override
    public Result modify(ClassVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        ClassInfo classInfo = super.getById(vo.getId());
        if(classInfo==null||classInfo.getIsDeleted().equals(BaseVo.DELETED)){
            return Result.error("班级不存在或者已删除");
        }
        if(!classInfo.getRelationTeacherId().equals(userDto.getTeacherInfo().getId())){
            return Result.error("只能操作自己的班级");
        }
        // 判断是否需要操作数据库
        boolean flag = false;

        if(!StringUtils.isBlank(vo.getClassName())){
            classInfo.setClassName(vo.getClassName());
            flag = true;
        }

        if(!StringUtils.isBlank(vo.getPracticeTitle())){
            classInfo.setPracticeTitle(vo.getPracticeTitle());
            flag = true;
        }

        if(!StringUtils.isBlank(vo.getPracticeContent())){
            classInfo.setPracticeContent(vo.getPracticeContent());
            flag = true;
        }

        if(flag) {
            classInfo.setUpdatedBy(userDto.getId());
            boolean update = super.updateById(classInfo);
            return update ? Result.success("更新成功") : Result.error("更新失败");
        } else {
            return Result.success("无需更新");
        }
    }

    @Override
    public Result deleted(ClassVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        ClassInfo classInfo = super.getById(vo.getId());
        if(classInfo==null||classInfo.getIsDeleted().equals(BaseVo.DELETED)){
            return Result.error("班级不存在或者已删除");
        }
        if(!classInfo.getRelationTeacherId().equals(userDto.getTeacherInfo().getId())){
            return Result.error("只能删除自己的班级");
        }
        classInfo.setIsDeleted(BaseVo.DELETED); //is_deleted修改为1
        classInfo.setUpdatedBy(userDto.getId());
        boolean b = super.updateById(classInfo);
        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getClassList(ClassVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        //缓存中获取当前登入用户（房紫写的）

        /**mybatis-plus的条件查询
         * select * from class_info where
         * relation_teacher_id = xxx and
         * is_deleted = 0 and
         * class_name like '%key%'
         */
        QueryWrapper<ClassInfo> wrapper = new QueryWrapper<ClassInfo>()
                .eq("relation_teacher_id", userDto.getTeacherInfo().getId())
                .eq("is_deleted", BaseVo.NOT_DELETED);
        if(!StringUtils.isBlank(vo.getKey())){
            wrapper.like("class_name", vo.getKey());
        }
        // 返回对象
        HashMap<String, Object> map = new HashMap<>();
        // map.put("1", "cy");
        // map.put("2", "fz");
        // map.get("1");//返回cy

        // 分页 1-列表 2-总数 3-总页数
        if (vo.getPage()!=null && vo.getPage()>0){
            // 分页插件
            Page<ClassInfo> page = new Page<>(vo.getPage(), vo.getSize());
            // 查询
            IPage<ClassInfo> iPage = super.page(page, wrapper);
            map.put("list", iPage.getRecords());
            map.put("pages", iPage.getPages());
            map.put("total", iPage.getTotal());
        } else {
            // 不分页 1-列表
            List<ClassInfo> list = super.list(wrapper);
            map.put("list", list);
        }
        return Result.success("请求成功", map);
    }
}
