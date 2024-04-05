package com.practice.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.ResourceVo;
import com.practice.entity.ImgsInfo;
import com.practice.entity.ResourceInfo;
import com.practice.mapper.ResourceInfoMapper;
import com.practice.service.ImgsInfoService;
import com.practice.service.ResourceInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.practice.utils.MyFileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class ResourceInfoServiceImpl extends ServiceImpl<ResourceInfoMapper, ResourceInfo> implements ResourceInfoService {
    @Autowired
    ImgsInfoService imgsInfoService;
    @Override
    public Result addResource(ResourceVo vo, MultipartFile[] files) throws IOException {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        Long imgsId = MyFileUtil.uploadFiles(files, "resourceInfo");
        ImgsInfo imgsInfo = imgsInfoService.getOne(new QueryWrapper<ImgsInfo>()
                .eq("imgs_id", imgsId).last("limit 1"));

        ResourceInfo resourceInfo = new ResourceInfo();
        resourceInfo.setResourceName(vo.getResourceName())
                .setResourceType(2)
                .setResourceUrl(imgsInfo.getImgUrl())
                .setCreatedBy(userDto.getId())
                .setRelationClassId(vo.getRelationClassId())
                .setRelationTeacherId(userDto.getTeacherInfo().getId());
        boolean save = super.save(resourceInfo);

        return save ? Result.success("添加成功") : Result.error("添加失败");
    }

    @Override
    public Result deletedResource(ResourceVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        ResourceInfo resourceInfo = super.getById(vo.getId());
        if(resourceInfo==null || resourceInfo.getIsDeleted()==BaseVo.DELETED){
            return Result.error("资源已删除");

        }
        if(!resourceInfo.getRelationTeacherId().equals(userDto.getTeacherInfo().getId())){
            return Result.error("只能删除自己上传的资源");
        }
        resourceInfo.setIsDeleted(BaseVo.DELETED);

        boolean b = super.updateById(resourceInfo);

        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getResourceList(ResourceVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        Page<ResourceInfo> page = new Page<>(vo.getPage(),vo.getSize());

        QueryWrapper<ResourceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", BaseVo.NOT_DELETED);
        Integer userType = userDto.getUserType();

        if(userType.equals(1)){
            wrapper.eq("relation_teacher_id", userDto.getTeacherInfo().getId());
        } else if(userType.equals(2)){
            vo.setRelationClassId(userDto.getStudentInfo().getRelationClassId());
        } else {
            return Result.error("未知身份");
        }
        if(vo.getRelationClassId() != null) {
            wrapper.eq("relation_class_id", vo.getRelationClassId());
        }
        if(!StringUtils.isBlank(vo.getKey())){
            wrapper.like("resource_name", vo.getKey());
        }
        Page<ResourceInfo> iPage = super.page(page, wrapper);

        // HashMap<String, Object> map = new HashMap<>();
        // map.put("list", page.getRecords());
        // return Result.success("请求成功", map);

        return Result.success("请求成功", MapBuilder.create()
                .put("list", iPage.getRecords())
                .put("total", iPage.getTotal())
                .put("pages", iPage.getPages())
                .map());
    }
}
