package com.practice.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.practice.common.dto.DiscussDto;
import com.practice.common.dto.UserDto;
import com.practice.common.lang.Result;
import com.practice.common.vo.BaseVo;
import com.practice.common.vo.DiscussVo;
import com.practice.entity.DiscussInfo;
import com.practice.entity.ImgsInfo;
import com.practice.mapper.DiscussInfoMapper;
import com.practice.mapper.ImgsInfoMapper;
import com.practice.service.DiscussInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.practice.service.ImgsInfoService;
import com.practice.utils.MyFileUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 答疑表 服务实现类
 * </p>
 *
 * @author 第五组
 * @since 2022-10-14
 */
@Service
public class DiscussInfoServiceImpl extends ServiceImpl<DiscussInfoMapper, DiscussInfo> implements DiscussInfoService {

    @Autowired
    ImgsInfoService imgsInfoService;
    @Autowired
    ImgsInfoMapper imgsInfoMapper;

    @Override
    public Result addDiscuss(DiscussVo vo, MultipartFile[] files) throws IOException {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        DiscussInfo discussInfo = new DiscussInfo();

        if(files != null) {
            Long imgsId = MyFileUtil.uploadFiles(files, "discussInfo");
            discussInfo.setRelationImgsId(imgsId);
        }

        if(userDto.getUserType()==2){
            vo.setRelationClassId(userDto.getStudentInfo().getRelationClassId());
            discussInfo.setRelationStudentId(userDto.getStudentInfo().getId());
        } else {
            discussInfo.setRelationTeacherId(userDto.getTeacherInfo().getId());
        }
        if(vo.getRelationClassId()==null){
            return Result.error("缺少班级id");
        }

        if(vo.getRelationDiscussId() != null){
            discussInfo.setRelationDiscussId(vo.getRelationDiscussId());
        }

        discussInfo.setContent(vo.getContent());
        discussInfo.setRelationClassId(vo.getRelationClassId());
        discussInfo.setCreatedBy(userDto.getId());
        boolean save = super.save(discussInfo);
        return save ? Result.success("发布成功") : Result.error("发布失败");
    }

    @Override
    public Result deletedDiscuss(DiscussVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        DiscussInfo discussInfo = super.getById(vo.getId());
        if(discussInfo==null || discussInfo.getIsDeleted().equals(BaseVo.DELETED)){
            return Result.error("该答疑不存在或已删除");
        }
        if(!discussInfo.getCreatedBy().equals(userDto.getId())){
            return Result.error("只能删除自己发布的答疑");
        }
        discussInfo.setIsDeleted(BaseVo.DELETED);
        boolean b = super.updateById(discussInfo);
        return b ? Result.success("删除成功") : Result.error("删除失败");
    }

    @Override
    public Result getDiscussList(DiscussVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        if(userDto.getUserType()==1){
            vo.setTeacherId(userDto.getTeacherInfo().getId());
        }
        List<DiscussDto> list = super.baseMapper.getListPage(vo);
        Integer total = super.baseMapper.getListPageCount(vo);

        for(DiscussDto discussDto : list) {
            if(discussDto.getRelationImgsId() != -1) {
                List<String> urlList = imgsInfoMapper.getUrlListByImgsId(discussDto.getRelationImgsId());
                discussDto.setImgsInfoUrlList(urlList);
            }
        }
        return Result.success("请求成功", MapBuilder.create()
                .put("list", list)
                .put("total", total)
                .put("pages", BaseVo.calculatePages(vo.getSize(), total))
                .map());
    }

    @Override
    public Result getReplyList(DiscussVo vo) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();

        List<DiscussDto> replyList = super.baseMapper.getReplyList(vo);

        for(DiscussDto discussDto : replyList) {
            if(discussDto.getRelationImgsId() != -1) {
                List<String> urlList = imgsInfoMapper.getUrlListByImgsId(discussDto.getRelationImgsId());
                discussDto.setImgsInfoUrlList(urlList);
            }
        }
        return Result.success("请求成功", MapBuilder.create()
                .put("replyList", replyList)
                .map());
    }
}
