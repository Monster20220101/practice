package com.practice.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.dto.DailyDto;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.DailyReportVo;
import com.practice.entity.ClassInfo;
import com.practice.entity.DailyReport;
import com.practice.mapper.ClassInfoMapper;
import com.practice.mapper.DailyReportMapper;
import com.practice.service.DailyReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 日报表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class DailyReportServiceImpl extends ServiceImpl<DailyReportMapper, DailyReport> implements DailyReportService {

    @Autowired
    private ClassInfoMapper classInfoMapper;

    @Override
    public Result addDailyReport(DailyReportVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        String title = vo.getTitle();
        String content = vo.getContent();

        ClassInfo classInfo = classInfoMapper.selectById(userDto.getStudentInfo().getRelationClassId());
        DailyReport dailyReport = new DailyReport();
        dailyReport.setContent(content).setTitle(title);
        dailyReport.setCreatedBy(userDto.getId());
        dailyReport.setRelationClassId(classInfo.getId());
        dailyReport.setRelationTeacherId(classInfo.getRelationTeacherId());
        dailyReport.setRelationStudentId(userDto.getStudentInfo().getId());
        boolean save = super.save(dailyReport);
        return save ? Result.success("添加成功") : Result.error("添加失败");
    }

    @Override
    public Result addCommentDailyReport(UserDto userDto, DailyReportVo vo) {
        DailyReport dailyReport = super.getById(vo.getId());

        if(dailyReport==null) {
            return Result.error("该日报已删除");
        }

        if(StringUtils.isBlank(vo.getComment())) {
            return Result.error("请填写评价内容");
        }

        // 判断学生日报关联的教师id是否相同
        if(!dailyReport.getRelationTeacherId().equals(userDto.getTeacherInfo().getId())) {
            return Result.error("无法评价");
        }

        dailyReport.setComment(vo.getComment());
        dailyReport.setUpdatedBy(userDto.getId());

        boolean b = super.updateById(dailyReport);

        return b ? Result.success("评价成功") : Result.error("评价失败");
    }

    @Override
    public Result modifyDailyReport(UserDto userDto, DailyReportVo vo) {
        DailyReport dailyReport = super.getById(vo.getId());

        if(dailyReport==null || dailyReport.getIsDeleted()==1) {
            return Result.error("该记录已删除");
        }

        boolean flag = false;

        if(!StringUtils.isBlank(vo.getTitle())) {
            if(!vo.getTitle().equals(dailyReport.getTitle())) {
                dailyReport.setTitle(vo.getTitle());
                flag = true;
            }
        }

        if(!StringUtils.isBlank(vo.getContent())) {
            if(!vo.getContent().equals(dailyReport.getContent())) {
                dailyReport.setContent(vo.getContent());
                flag = true;
            }
        }

        if(flag) {
            dailyReport.setUpdatedBy(userDto.getId());
            boolean b = super.updateById(dailyReport);

            return b ? Result.success("修改成功") : Result.error("修改失败");
        }

        return Result.success("无需修改");
    }

    @Override
    public Result deletedDailyReport(DailyReportVo vo) {
        DailyReport dailyReport = super.getById(vo.getId());
        if(dailyReport==null || dailyReport.getIsDeleted() == BaseVo.DELETED) {
            return Result.error("该日报已删除");
        }
        dailyReport.setIsDeleted(BaseVo.DELETED);
        boolean b = super.updateById(dailyReport);
        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getDailyReportList(DailyReportVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        if(userDto.getUserType()==1) {
            QueryWrapper<ClassInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("relation_teacher_id",
                    userDto.getTeacherInfo().getId());
            List<ClassInfo> classInfos = classInfoMapper.selectList(wrapper);
            List<Long> idList = new ArrayList<>();

            for(ClassInfo info : classInfos) {
                idList.add(info.getId());
            }

            System.err.println(idList);

            vo.setClassList(idList);

        } else if (userDto.getUserType()==2) {
            vo.setUserId(userDto.getId());
        } else {
            return Result.error("无权限");
        }
        List<DailyDto> list = super.baseMapper.getDailyReportList(vo);
        Integer total = super.baseMapper.getDailyReportListCount(vo);

        // HashMap<Integer, String> map = new HashMap<>();
        // map.put(1, "chf");
        // String name = map.get(1); // 获取到“chf”

        return Result.success("请求成功", MapBuilder.create()
                .put("list", list)
                .put("total", total)
                .put("pages", BaseVo.calculatePages(vo.getSize(), total))
                .map());
    }
}
